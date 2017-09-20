import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class OverWriteEquals {

	public static void main(String[] args) {
		
		/*
		 // Arrays.asList() 看起来是将数组转化为列表，但其实再内存中仍然是数组，因此不能使用列表的add和remove操作
      String[] ss = new String[3];
      ss[0] = "11";
      ss[1] = "22";
      List<String> asList = Arrays.asList(ss);
//      asList.add("55");
      System.out.println(asList);
//      asList.remove("11");
      System.out.println(asList);
      */
		
//		        Collection c = new HashSet();
//		        c.add("hello");
//		        c.add(new Name("f1","l1"));
//		        c.add(new Integer(100));
//		        c.remove("hello"); 
//		        c.remove(new Integer(100));
//		        System.out.println(c.remove(new Name("f1","l1")));
		 
		        
		Name n1 = new Name("01");
		Name n2 = new Name("01");

		Collection<Name> c = new HashSet<Name>();
		c.add(n1);
		System.out.println("------------");
		c.add(n2);
		System.out.println("------------");
		System.out.println(n1.equals(n2));
		System.out.println("------------");
		System.out.println(n1.hashCode() == n2.hashCode());
		System.out.println(n1.hashCode());
		System.out.println(n2.hashCode());
		System.out.println(c);
	}


}

class Name {
	private String id;
	public Name(String id) {
		this.id = id; 
	}

	public String toString(){
		return this.id;
	}
	public boolean equals(Object obj) {
		if (obj instanceof Name) {
			Name name = (Name) obj;
			System.out.println("equal"+ name.id);
			return (id.equals(name.id));
		}
		return super.equals(obj);
	}

	public int hashCode() {
		Name name = (Name) this;
		System.out.println("Hash" + name.id);
		return id.hashCode();

	}


}
