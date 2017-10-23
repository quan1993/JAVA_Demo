package classInit;

public class Parent {
	static {  
		System.out.println("父类：静态代码块");  
	}  

	{  
		System.out.println("父类：普通代码块");  
	}  

	@SuppressWarnings("unused")
	private static String staticStringInParent = initStaticStringInParent();  

	@SuppressWarnings("unused")
	private String stringInParent = initStringInParent();  

	public Parent() {  
		System.out.println("父类：构造方法");  
	}  

	private static String initStaticStringInParent() {  
		System.out.println("父类：静态方法，被静态成员变量赋值调用。");  
		return "initStaticStringInParent";  
	}  
	private String initStringInParent() {  
		System.out.println("父类：普通成员方法，被普通成员变量赋值调用。");  
		return "initStringInParent";  
	}  
	@SuppressWarnings("unused")
	private String stringInParent() {  
		System.out.println("父类：普通成员方法，未被调用。");  
		return "stringInParent";  
	}  
}
