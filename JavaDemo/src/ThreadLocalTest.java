
public class ThreadLocalTest { 
	
	/**
	 * ThreadLocal为每个使用该变量的线程提供独立的变量副本，所以每一个线程都可以独立地改变自己的副本，
	 * 而不会影响其它线程所对应的副本。
	 */
	// ①通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值  
    private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {  
        public Integer initialValue() {  
            return 0;  
        }  
    };  
  
    // ②获取下一个序列值  
    public int getNextNum() {  
        seqNum.set(seqNum.get() + 1);  
        return seqNum.get();  
    }  
  
    public static void main(String[] args) {  
    	ThreadLocalTest test = new ThreadLocalTest();  
        // ③ 3个线程共享sn，各自产生序列号  
        TestClient t1 = new TestClient(test);  
        TestClient t2 = new TestClient(test);  
        TestClient t3 = new TestClient(test);  
        t1.start();  
        t2.start();  
        t3.start();  
    }  
  
}

class TestClient extends Thread {  
    private ThreadLocalTest sn;  

    public TestClient(ThreadLocalTest sn) {  
        this.sn = sn;  
    }  

    public void run() {  
        for (int i = 0; i < 3; i++) {  
            // ④每个线程打出3个序列值  
            System.out.println("thread[" + Thread.currentThread().getName() 
            		+ "] --> sn[" + sn.getNextNum() + "]");  
        }  
    }  
}  