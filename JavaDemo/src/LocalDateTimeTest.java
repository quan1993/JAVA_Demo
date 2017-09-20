import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTest {
	
	/**
	 * 获得指定地区当前时间<br>
	 * SimpleDateFormat是线程不安全的类，一般不要定义为static<br>
	 * 可用DateTimeFormatter代替SimpleDateFormatter
	 * @param args
	 */

	public static void main(String[] args) {
		ZoneId zoneId = ZoneId.of("Asia/Shanghai");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String now = LocalDateTime.now(zoneId).format(formatter);
		System.out.println(now);
		
//		Instant instant = Instant.now(Clock.systemUTC());
		Instant instant = Instant.now(Clock.system(zoneId));
        System.out.println(instant);
        System.out.println(Clock.systemUTC().toString());
        
        /*   将获取的long形式时间转为指定的时间格式  */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        String ss = simpleDateFormat.format(time);
        System.out.println("time ：" + ss);
	}

}
