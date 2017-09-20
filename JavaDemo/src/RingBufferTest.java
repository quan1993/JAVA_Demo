import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkHandler;
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

	static class IntEventExceptionHandler implements ExceptionHandler {  
		public void handleEventException(Throwable ex, long sequence, Object event) {}  
		public void handleOnStartException(Throwable ex) {}  
		public void handleOnShutdownException(Throwable ex) {}  
	} 


}

class Producer {

	private final RingBuffer<Order> ringBuffer;

	public Producer(RingBuffer<Order> ringBuffer){
		this.ringBuffer = ringBuffer;
	}

	/**
	 * onData用来发布事件，每调用一次就发布一次事件
	 * 它的参数会用过事件传递给消费者
	 */
	public void onData(String data){
		//可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
		long sequence = ringBuffer.next();
		try {
			//用上面的索引取出一个空的事件用于填充（获取该序号对应的事件对象）
			Order order = ringBuffer.get(sequence);
			//获取要通过事件传递的业务数据
			order.setId(data);
		} finally {
			//发布事件
			//注意，最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
			ringBuffer.publish(sequence);
		}
	}


}

class Consumer implements WorkHandler<Order>{

	private String consumerId;

	private static AtomicInteger count = new AtomicInteger(0);

	public Consumer(String consumerId){
		this.consumerId = consumerId;
	}

	@Override
	public void onEvent(Order order) throws Exception {
		System.out.println("当前消费者: " + this.consumerId + "，消费信息：" + order.getId() + "count:" + count.get());
		count.incrementAndGet();
	}

	public int getCount(){
		return count.get();
	}

}

class Order {  

	private String id;//ID  
	private String name;
	private double price;//金额  

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

} 

class OrderFactory implements EventFactory<Order> {

	@Override
	public Order newInstance() {
		return new Order();
	}
	
}
