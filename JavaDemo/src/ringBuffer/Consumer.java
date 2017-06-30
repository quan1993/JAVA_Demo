package ringBuffer;

import java.util.concurrent.atomic.AtomicInteger;

import com.lmax.disruptor.WorkHandler;

public class Consumer implements WorkHandler<Order>{
	private String consumerId;

	private static AtomicInteger count = new AtomicInteger(0);

	public Consumer(String consumerId){
		this.consumerId = consumerId;
	}

	@Override
	public void onEvent(Order order) throws Exception {
		//count.get()得到的值可能相同
		System.out.println("当前消费者: " + this.consumerId + "，消费信息：" + order.getId() + "count:" + count.get());
		//count的增加操作是原子性的，线程安全
		count.incrementAndGet();
	}

	public int getCount(){
		return count.get();
	}

}
