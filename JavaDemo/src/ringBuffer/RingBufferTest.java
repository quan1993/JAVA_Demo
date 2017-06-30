package ringBuffer;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

public class RingBufferTest {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		OrderFactory orderFactory = new OrderFactory();
		//创建ringBuffer
		RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI, 
						orderFactory, 1024 * 1024, new YieldingWaitStrategy());

		SequenceBarrier barriers = ringBuffer.newBarrier();

		Consumer[] consumers = new Consumer[3];
		for(int i = 0; i < consumers.length; i++){
			consumers[i] = new Consumer("c" + i);
		}

		WorkerPool<Order> workerPool = 
				new WorkerPool<Order>(ringBuffer, barriers, new IntEventExceptionHandler(), consumers);

		ringBuffer.addGatingSequences(workerPool.getWorkerSequences());  
		workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));  

		final CountDownLatch latch = new CountDownLatch(1);
		for (int i = 0; i < 10; i++) {  
			final Producer producer = new Producer(ringBuffer);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						latch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for(int j = 0; j < 10; j++){
						producer.onData("j=" + j + "  " + UUID.randomUUID().toString());
					}
				}
			}).start();
		} 
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("---------------开始生产-----------------");
		latch.countDown();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("总数:" + consumers[0].getCount() );
	}

	@SuppressWarnings("rawtypes")
	static class IntEventExceptionHandler implements ExceptionHandler {  
		public void handleEventException(Throwable ex, long sequence, Object event) {}  
		public void handleOnStartException(Throwable ex) {}  
		public void handleOnShutdownException(Throwable ex) {}  
	} 



}
