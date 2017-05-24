import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CloseExecutorService {
	/**
	 * 结束ExecutorService<br>
	 * shutdown()并不会结束正在进行和等待的进程,executor不会再接收新任务<br>
	 * shutdownNow()会结束正在进行的进程和等待进程
	 * @param args
	 */

	public static void main(String[] args) {  
		   
	    ExecutorService pool = Executors.newFixedThreadPool(5);  
	    final long waitTime = 8 * 1000;  
	    final long awaitTime = 5 * 1000;  
	   
	    Runnable task1 = new Runnable(){  
	        public void run(){  
	            try {  
	                System.out.println("task1 start");  
	                Thread.sleep(waitTime);  
	                System.out.println("task1 end");  
	            } catch (InterruptedException e) {  
	                System.out.println("task1 interrupted: " + e.getMessage());  
	            }  
	        }  
	    };  
	   
	    Runnable task2 = new Runnable(){  
	        public void run(){  
	            try {  
	                System.out.println("  task2 start");  
	                Thread.sleep(1000);  
	                System.out.println("  task2 end");  
	            } catch (InterruptedException e) {  
	                System.out.println("task2 interrupted: " + e.getMessage());  
	            }  
	        }  
	    };  
	    pool.execute(task1);  
	   
	    for(int i = 0; i < 1000; ++i){  
	        pool.execute(task2);  
	    }  
	   
	    try {  
	    	System.out.println("shutdown before--");
	        pool.shutdown();  
	        System.out.println("shutdown end--");
	   
	        if(!pool.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)){  
	        	System.out.println("shutdown before --");
	            pool.shutdownNow();  
	            System.out.println("shutdown end --");
	        }  
	    } catch (InterruptedException e) {  
	        System.out.println("awaitTermination interrupted: " + e);  
	        pool.shutdownNow();  
	    }  
	   
	    System.out.println("end");  
	}  
}
