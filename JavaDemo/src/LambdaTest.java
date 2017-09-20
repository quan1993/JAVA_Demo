import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaTest {

	public static void main(String[] args) {
		// 将字符串换成大写并用逗号链接起来
		List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
		String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
		System.out.println(G7Countries);
		
		 List<String> languages = Arrays.asList("Java", "Scala", "C", "C++", "Haskell", "Lisp");
		 List<String> filtered = languages.stream().filter(x -> x.length()> 2).collect(Collectors.toList());
		 System.out.printf("Original List : %s, filtered list : %s", languages, filtered);

	}

}
