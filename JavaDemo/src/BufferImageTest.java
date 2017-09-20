import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class BufferImageTest {

	public static void main(String[] args) {

//		BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);
		
		
	}

}

class Server {
	public void run(){
		try{
			ServerSocket server = new ServerSocket(8888);
			Socket s = server.accept();
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			BufferedImage image = ImageIO.read(new File("D:\netty1.jpg"));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			boolean flag = ImageIO.write(image, "jpg", out);
			byte[] b = out.toByteArray();
			dout.write(b);
			s.close();
		} catch (Exception e){
			
		}
	}
}

class Client {
	public void run(){
		
	}
}
