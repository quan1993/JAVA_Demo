import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IP2Int {

	public static void main(String[] args) {

     IP2Int iptrans = new IP2Int();
     System.out.println(iptrans.isIPv4Address("10.10l.10.11"));
     System.out.println(iptrans.isIPv4Address("10.10l.10.256"));
     System.out.println(iptrans.IP2int("10.101.10.11"));
	}

	public int IP2int(String ip){
		String[] split = ip.split(".");
		byte[] ipbyte = new byte[4];
		ipbyte[0] = (byte) Integer.parseInt(split[0]); 
		ipbyte[1] = (byte) Integer.parseInt(split[1]); 
		ipbyte[2] = (byte) Integer.parseInt(split[2]); 
		ipbyte[3] = (byte) Integer.parseInt(split[3]);  
		int ipAddress = ipbyte[3] & 0xFF;
		ipAddress |= ((ipbyte[2] << 8) & 0xFF00);
		ipAddress |= ((ipbyte[1] << 16) & 0xFF0000);
		ipAddress |= ((ipbyte[0] << 24) & 0xFF000000);
		return ipAddress;
	}

	public String int2bytes(int ipInt){
		byte[] ipAddress = new byte[4];
		ipAddress[0] = (byte) ((ipInt >>> 24) & 0xFF);
		ipAddress[1] = (byte) ((ipInt >>> 16) & 0xFF);
		ipAddress[2] = (byte) ((ipInt >>> 8) & 0xFF);
		ipAddress[3] = (byte) (ipInt & 0xFF);
		return null;

	}

	public static int bytesToInt(byte[] bytes) {
		int addr = bytes[3] & 0xFF;
		addr |= ((bytes[2] << 8) & 0xFF00);
		addr |= ((bytes[1] << 16) & 0xFF0000);
		addr |= ((bytes[0] << 24) & 0xFF000000);
		return addr;
	}

	public static String intToIp(int ipInt) {
		return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.')
				.append((ipInt >> 16) & 0xff).append('.').append(
						(ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff))
				.toString();
	}

	/**
	 * 检测IP地址是否符合标准
	 * @param ip
	 * @return
	 */
	public boolean isIPv4Address(String ip){
		String lower = "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])";  //0-255的数字
		String regex = lower + "\\." + lower + "{3}";
		System.out.println(regex);
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

}
