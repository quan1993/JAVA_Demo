import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class TestDataStreamClient {  
	public static void main(String[] args) {  
		try {
			
			System.out.println(" " + String.valueOf(System.currentTimeMillis()));
//			Socket socket = new Socket("10.10.10.10", 10009);



			//		try {
						Socket socket = new Socket();
						socket.connect(new InetSocketAddress("10.10.10.10", 10009), 3000);
			//			//			socket.setSoTimeout(3000);
			//
			//			//				Thread.sleep(5000);
			//
			//			Date date1 = new Date();
			//			System.out.println(" date1" + date1);
			//			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			//			dos.writeBytes("世界");
			//			//按2字节写入，都是写入的低位  
			//			dos.writeChars("世界"); // 按照Unicode写入  
			//			// 按照UTF-8写入(UTF8变长，开头2字节是由writeUTF函数写入的长度信息，方便readUTF函数读取)  
			//			dos.writeUTF("世界"); 
			//
			//			dos.flush();  
			//			//			dos.close();  
			//			while(true){
			//				Thread.sleep(1000);
			//				Date date3 = new Date();
			//				System.out.println(" date3" + date3);
			//				System.out.println(socket.getSoTimeout());
			//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Date date4 = new Date();
			System.out.println(" date4" + date4);
			e.printStackTrace();
		} 



		//		} catch (Exception e){
		////			System.out.println("run  error" + e.getMessage());
		//			Date date2 = new Date();
		//			System.out.println(" " + date2);
		//			System.out.println(e);
		//		}







		//        // 使用DataInputStream,DataOutputStream写入文件且从文件中读取数据。  
		//        try {  
		//            // Data Stream写到输入流中  
		//            DataOutputStream dos = new DataOutputStream(new FileOutputStream(  
		//                    "datasteam.txt"));  
		//            dos.writeBytes("世界"); //按2字节写入，都是写入的低位  
		//            dos.writeChars("世界"); // 按照Unicode写入  
		//            // 按照UTF-8写入(UTF8变长，开头2字节是由writeUTF函数写入的长度信息，方便readUTF函数读取)  
		//            dos.writeUTF("世界");   
		//            dos.flush();  
		//            dos.close();  
		//  
		//            // Data Stream 读取  
		//            DataInputStream dis = new DataInputStream(new FileInputStream(  
		//                    "datasteam.txt"));  
		//            // 读取字节  
		//            byte[] b = new byte[2];  
		//            dis.read(b);  
		//            System.out.println(new String(b, 0, 2));  
		//  
		//            // 读取字符  
		//            char[] c = new char[2];  
		//            for (int i = 0; i < 2; i++) {  
		//                c[i] = dis.readChar();  
		//            }  
		//            System.out.println(new String(c, 0, 2));  
		//  
		//            // 读取UTF  
		//            System.out.println(dis.readUTF());  
		//  
		//            dis.close();  
		//        } catch (IOException e) {  
		//            e.printStackTrace();  
		//        }  
	}  
} 
