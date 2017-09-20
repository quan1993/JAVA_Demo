/**
 * 测试线程中join()方法
 * @author ampthon
 *
 */
public class JoinTest {
	public static void main(String[] args) {
		System.out.println("start");
		Thread th = new Thread(new Worker(), "worker1");
		th.start();
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}
}

class Worker implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "----start");	

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "----end");	
	}
}