
/**
 * 枚举类中属性值与数字之间的相互转化
 * @author ampthon
 *
 */
public class EnumConvert {

	public static void main(String[] args) {
		System.out.println(Appstatus.convertFrom(1));
		System.out.println(Appstatus.FINISHED);
		System.out.println(Appstatus.FINISHED.ordinal());
	}
}

enum Appstatus {
	INIT,
	RUNNING,
	FINISHED;
	
	public static Appstatus convertFrom(int ordinal){
		Appstatus[] types = Appstatus.values();
		return types[ordinal];
	}
}
