import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;
/**
 * 设置日志，将每天的日志分离，每天的日志存到不同的日志文件中<br>
 * 日志都是先存到日志文件中，当到第二天时，前一天的日志就会被提取出来，放到与前一天相对应的日志文件中
 * @author ampthon
 *
 */
public class Log4jTest extends DailyRollingFileAppender{

	
	public boolean isAsServereAsThreshold(Priority priority){
		return this.getThreshold().equals(priority);
	}
	
	public static void main(String[] args) {
		String aa = "aaab";
		System.out.println(" " + aa.length());
		System.out.println(" " + aa.getBytes().length);

	}

}
