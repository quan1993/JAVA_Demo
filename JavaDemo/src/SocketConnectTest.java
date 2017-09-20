import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketConnectTest {

	public static void main(String[] args) {
		/**
		 * 设置socket的连接超时时间
		 */
		Socket socket = new Socket();
		SocketAddress socAddress = null;
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss:SSS");
		System.out.println("start" + format.format(new Date()));

		try {
			socAddress = new InetSocketAddress(
					InetAddress.getByName("192.168.0.2"), 53534);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("111" + format.format(new Date()));
		} 
		try {
			socket.connect(socAddress, 6000);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("222" + format.format(new Date()));
		}
		
		
		System.out.println("start---222" + format.format(new Date()));
		try {
			Socket socket2 = new Socket("192.168.0.1", 56565);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end---222" + format.format(new Date()));
		
	}

}
