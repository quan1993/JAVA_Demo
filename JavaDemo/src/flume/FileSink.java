package flume;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.instrumentation.SinkCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.apache.flume.serialization.EventSerializer;
import org.apache.flume.serialization.EventSerializerFactory;
import org.apache.flume.sink.AbstractSink;

/**
 * 使用时，将工程导成jar包，放到libs目录下，在配置文件中做如下配置
 * agent.sinks.hdfssinkLvi24.type = flume.FileSink
agent.sinks.hdfssinkLvi24.sink.directory = /data/logs/flume/lvi/%Y%m%d/
agent.sinks.hdfssinkLvi24.sink.filePrefix = flume_bjxd04.%Y%m%d%H%M
agent.sinks.hdfssinkLvi24.sink.fileSuffix = .log
agent.sinks.hdfssinkLvi24.sink.rollInterval = 600
agent.sinks.hdfssinkLvi24.channel = memoryChannelLvi24
 * @author ampthon
 *
 */
public class FileSink  extends AbstractSink implements
Configurable {

	private static final Logger logger = LoggerFactory
			.getLogger(FileSink.class);
	private static final long defaultRollInterval = 30;
	private static final int defaultBatchSize = 100;

	private int batchSize = defaultBatchSize;

	private String directory;  //在 RollingFileSink类 是 private File directory; 因为此处需要替换 年月日等 定义为String 
	private long rollInterval;
	private OutputStream outputStream;
	private ScheduledExecutorService rollService;

	private String serializerType;
	private Context serializerContext;
	private EventSerializer serializer;

	private SinkCounter sinkCounter;

	private FilePathManager pathController;
	private volatile boolean shouldRotate;

	private String pefix; 
	private String suffix; 

	public FileSink() {
		pathController = new FilePathManager();
		shouldRotate = false;
	}


	@Override
	public void configure(Context context) {
		//<span style="white-space:pre">		//获取配置参数sink.directory</span>  sink.rollInterval  sink.filePrefix  sink.fileSuffix
		directory = context.getString("sink.directory");
		String rollInterval = context.getString("sink.rollInterval");
		pefix = context.getString("sink.filePrefix");  
		suffix = context.getString("sink.fileSuffix"); 

		serializerType = context.getString("sink.serializer", "TEXT");
		serializerContext = new Context(context.getSubProperties("sink."
				+ EventSerializer.CTX_PREFIX));

		Preconditions.checkArgument(directory != null,
				"Directory may not be null");
		Preconditions.checkNotNull(serializerType,
				"Serializer type is undefined");

		if (rollInterval == null) {
			this.rollInterval = defaultRollInterval;
		} else {
			this.rollInterval = Long.parseLong(rollInterval);
		}

		batchSize = context.getInteger("sink.batchSize", defaultBatchSize);

		if (sinkCounter == null) {
			sinkCounter = new SinkCounter(getName());
		}
	}

	@Override
	public void start() {
		logger.info("Starting {}...", this);
		sinkCounter.start();
		super.start();

		pathController.setBaseDirectory(directory);
		pathController.setPefix(pefix);
		pathController.setSuffix(suffix);

		if (rollInterval > 0) {

			rollService = Executors.newScheduledThreadPool(
					1,
					new ThreadFactoryBuilder().setNameFormat(
							"rollingFileSink-roller-"
									+ Thread.currentThread().getId() + "-%d")
					.build());

			/*
			 * Every N seconds, mark that it's time to rotate. We purposefully
			 * do NOT touch anything other than the indicator flag to avoid
			 * error handling issues (e.g. IO exceptions occuring in two
			 * different threads. Resist the urge to actually perform rotation
			 * in a separate thread!
			 */
			rollService.scheduleAtFixedRate(new Runnable() {

				@Override
				public void run() {
					logger.debug("Marking time to rotate file {}",
							pathController.getCurrentFile());
					shouldRotate = true;
				}

			}, rollInterval, rollInterval, TimeUnit.SECONDS);
		} else {
			logger.info("RollInterval is not valid, file rolling will not happen.");
		}
		logger.info("RollingFileSink {} started.", getName());
	}

	@Override
	public Status process() throws EventDeliveryException {
		if (shouldRotate) {   // shouldRotate为真，表示当前文件停止Roll，再生成新的文件执行写入
			logger.debug("Time to rotate {}", pathController.getCurrentFile());

			if (outputStream != null) {
				logger.debug("Closing file {}", pathController.getCurrentFile());

				try {
					serializer.flush();
					serializer.beforeClose();
					outputStream.close();
					sinkCounter.incrementConnectionClosedCount();
					shouldRotate = false;
				} catch (IOException e) {
					sinkCounter.incrementConnectionFailedCount();
					throw new EventDeliveryException("Unable to rotate file "
							+ pathController.getCurrentFile()
							+ " while delivering event", e);
				} finally {
					serializer = null;
					outputStream = null;
				}

				////去掉文件后缀名（文件在写入的过程中默认给加了.tmp作为区分，文件写完需要去掉这个后缀）
				File ff = pathController.getCurrentFile();
//				try {
					ff.renameTo(new File(ff.getAbsolutePath().substring(0, ff.getAbsolutePath().indexOf(".tmp"))));
//					FileUtils.moveFile( ff, new File(ff.getAbsolutePath().substring(0, ff.getAbsolutePath().indexOf(".tmp"))));
//				} catch (IOException e) {
//					e.printStackTrace();
//				}	
				pathController.rotate();
			}
		}

		if (outputStream == null) {
			File currentFile = pathController.getCurrentFile();
			logger.debug("Opening output stream for file {}", currentFile);
			try {
				outputStream = new BufferedOutputStream(new FileOutputStream(
						currentFile));
				serializer = EventSerializerFactory.getInstance(serializerType,
						serializerContext, outputStream);
				serializer.afterCreate();
				sinkCounter.incrementConnectionCreatedCount();
			} catch (IOException e) {
				sinkCounter.incrementConnectionFailedCount();
				throw new EventDeliveryException("Failed to open file "
						+ pathController.getCurrentFile()
						+ " while delivering event", e);
			}
		}

		Channel channel = getChannel();
		Transaction transaction = channel.getTransaction();
		Event event = null;
		Status result = Status.READY;

		try {
			transaction.begin();
			int eventAttemptCounter = 0;
			for (int i = 0; i < batchSize; i++) {
				event = channel.take();
				if (event != null) {
					sinkCounter.incrementEventDrainAttemptCount();
					eventAttemptCounter++;
					serializer.write(event);

					/*
					 * FIXME: Feature: Rotate on size and time by checking bytes
					 * written and setting shouldRotate = true if we're past a
					 * threshold.
					 */

					/*
					 * FIXME: Feature: Control flush interval based on time or
					 * number of events. For now, we're super-conservative and
					 * flush on each write.
					 */
				} else {
					// No events found, request back-off semantics from runner
					result = Status.BACKOFF;
					break;
				}
			}
			serializer.flush();
			outputStream.flush();
			transaction.commit();
			sinkCounter.addToEventDrainSuccessCount(eventAttemptCounter);


		} catch (Exception ex) {
			transaction.rollback();
			throw new EventDeliveryException("Failed to process transaction",
					ex);
		} finally {
			transaction.close();
		}

		return result;
	}

	@Override
	public void stop() {
		logger.info("RollingFile sink {} stopping...", getName());
		sinkCounter.stop();
		super.stop();

		if (outputStream != null) {
			logger.debug("Closing file {}", pathController.getCurrentFile());

			try {
				serializer.flush();
				serializer.beforeClose();
				outputStream.close();
				sinkCounter.incrementConnectionClosedCount();


			} catch (IOException e) {
				sinkCounter.incrementConnectionFailedCount();
				logger.error(
						"Unable to close output stream. Exception follows.", e);
			} finally {
				outputStream = null;
				serializer = null;
			}
		}
		if (rollInterval > 0) {
			rollService.shutdown();

			while (!rollService.isTerminated()) {
				try {
					rollService.awaitTermination(1, TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					logger.debug(
							"Interrupted while waiting for roll service to stop. "
									+ "Please report this.", e);
				}
			}
		}
		logger.info("RollingFile sink {} stopped. Event metrics: {}",
				getName(), sinkCounter);
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public long getRollInterval() {
		return rollInterval;
	}

	public void setRollInterval(long rollInterval) {
		this.rollInterval = rollInterval;
	}
}

