import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class GetAreaTime {
	
	/**
	 * �õ�ָ�������ĵ�ǰʱ��
	 * @param args
	 */

	public static void main(String[] args) {
		ZoneId zoneId = ZoneId.of("Asia/Tokyo");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String now = LocalDateTime.now(zoneId).format(formatter);
		System.out.println(now);
		

	}

}
