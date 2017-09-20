import java.util.concurrent.atomic.LongAdder;

public class LongAdderTest {

	/**
	 * jdk8,推荐使用LongAdder对象，比AtomicLong性能更好，减少乐观锁的重试次数
	 * @param args
	 */
	public static void main(String[] args) {
		LongAdder adder = new LongAdder();
		System.out.println(adder);
		adder.add(22L);
		System.out.println(adder);

	}

}
