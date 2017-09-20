import java.util.concurrent.CountDownLatch;

/**
 * 监控程序状态，将退出的程序再次新建
 * @author ampthon
 *
 */
public class WatchThread {


	/**
	 * 测试函数
	 *
	 * @throws InterruptedException
	 */
	public void testThread()throws InterruptedException {
		int threadNum =10;
		// 初始化countDown
		CountDownLatch threadSignal =new CountDownLatch(threadNum);
		
		new Thread(new Monitor(threadSignal)).start();
		
		for (int i = 0; i < threadNum; i++){
			new Thread(new TestThread(threadSignal)).start();
			Thread.sleep(100);
		}

		// 创建固定长度的线程池
		//		Executor executor = Executors.newFixedThreadPool(threadNum);
		//		for(int i =0; i < threadNum; i++) {// 开threadNum个线程
		//			Runnable task =new TestThread(threadSignal);
		//			// 执行
		//			executor.execute(task);
		//		}
		threadSignal.await();// 等待所有子线程执行完
		// do work
		System.out.println(Thread.currentThread().getName() +"+++++++结束.");
	}

	/**
	 * 测试函数
	 */
	public static void main(String[] args)throws InterruptedException {
		WatchThread test =new WatchThread();
		test.testThread();
	}

	/**
	 *
	 * @author jill
	 *
	 */
	private class TestThread implements Runnable {
		private CountDownLatch threadsSignal;

		public TestThread(CountDownLatch threadsSignal) {
			this.threadsSignal = threadsSignal;
		}

		public void run() {
			System.out.println(Thread.currentThread().getName() +"开始...");
			// do shomething
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("开始了线程：：：："+ threadsSignal.getCount());
			// 线程结束时计数器减1
			threadsSignal.countDown(); //这句代码 建议放在 finally里执行
			System.out.println(Thread.currentThread().getName() +"结束. 还有"
					+ threadsSignal.getCount() +" 个线程");

			synchronized (this) {

			}
		}
	}

	class Monitor implements Runnable{

		private CountDownLatch threadsSignal;

		public Monitor(CountDownLatch threadsSignal) {
			this.threadsSignal = threadsSignal;
		}
		@Override
		public void run() {
			while (true) {
				if (threadsSignal.getCount() <= 6) {
					new Thread(new TestThread(threadsSignal)).start();
					System.out.println("重启线程");
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}


}
