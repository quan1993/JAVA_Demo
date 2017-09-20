import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

	public static void main(String[] args) {
		Pattern pattern = Pattern.compile("[1-9][0-9]*");
		Matcher matcher = pattern.matcher("1");
		if(matcher.find()){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	}

}
