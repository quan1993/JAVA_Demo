

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class GetMD5 {

	public static void main(String[] args) {
		/**
		 * 根据文件内容获得MD5值，然后比较不同时刻的MD5值，判断文件内容是否发生改变
		 */

      try {
		MessageDigest instance = MessageDigest.getInstance("MD5");
		FileInputStream fileInputStream = new FileInputStream("D:/add.txt");
		int length = -1;
		byte buffer[] = new byte[1024];
		while((length = fileInputStream.read(buffer, 0, 1024)) != -1){
			instance.update(buffer, 0, length);
		}
		BigInteger bigInteger = new BigInteger(1, instance.digest());
		String value = bigInteger.toString(16); //将bigInteger以十六进制的形式转化为String
		
		System.out.println(value);
		fileInputStream.close();
	} catch (Exception e) {
		e.printStackTrace();
	}

	}

}
