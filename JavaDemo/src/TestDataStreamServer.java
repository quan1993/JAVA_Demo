import java.io.BufferedInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TestDataStreamServer {  
	public static void main(String[] args) {  
		try {

			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(10009);
			Socket server = serverSocket.accept();
//			server.setSoTimeout(5000);
			
			BufferedInputStream input = new BufferedInputStream(server.getInputStream());
			byte[] bytes = new byte[1024];
			int length = 0;
			int j = 0;
			while ((length = input.read(bytes)) > 0){
				
				while (j < length){
					System.out.print(bytes[j]);
					j++;
				}
				Date date1 = new Date();
				System.out.println(" date1" + date1);
				
				System.out.println(new String(bytes, 0, length));
			}

//			input1.read(bytes1);
			Date date2 = new Date();
			System.out.println(" date1" + date2);
			
			
		} catch (Exception e){
			Date date1 = new Date();
			System.out.println(" date_catch" + date1);
                 System.out.println("error" + e.getMessage());
		}

	}  
} 
