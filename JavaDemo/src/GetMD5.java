

import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class GetMD5 {

	public static void main(String[] args) {
		/**
		 * �����ļ����ݻ��MD5ֵ��Ȼ��Ƚϲ�ͬʱ�̵�MD5ֵ���ж��ļ������Ƿ����ı�
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
		String value = bigInteger.toString(16); //��bigInteger��ʮ�����Ƶ���ʽת��ΪString
		
		System.out.println(value);
		fileInputStream.close();
	} catch (Exception e) {
		e.printStackTrace();
	}

	}

}
