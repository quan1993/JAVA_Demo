package flume;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileSinkDefinedUtils {
	/**
	 * 功能：替换文件夹路径中的%Y%m%d <br/>
	 *
	 * @author pjm <br/>
	 * @version 2015-1-15 上午09:44:46 <br/>
	 */
	public static String getRealPath(String path){
        if (path.contains("%Y%m%d")) {
        	Date today = new Date(); 
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
        	String formattedDate = formatter.format(today); 
        	System.out.println(formattedDate); 
        	path = path.replace("%Y%m%d", formattedDate);
		}
		return path;
	}
	
	/**
	 * 功能： 文件前缀替换<br/>
	 *
	 * @author pjm <br/>
	 * @version 2015-1-15 上午09:45:32 <br/>
	 */
	public static String getRealPathFilePrefix(String path){
		  if (path.contains("%Y%m%d%H%M")) {
	        	Date today = new Date(); 
	        	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm"); 
	        	String formattedDate = formatter.format(today); 
	        	System.out.println(formattedDate); 
	        	path = path.replace("%Y%m%d%H%M", formattedDate);
			}
		return path;
	}
	
	/**
	 * 功能： 创建文件和文件夹，并返回文件<br/>
	 *
	 * @author pjm <br/>
	 * @version 2015-1-15 上午09:45:48 <br/>
	 */
	public static File CreateFolderAndFile(String dirpath,String filepath){
		
//		String dirpath  = "/data/logs/flume/All/20150115/";
//		String filepath = "/data/logs/flume/All/20150115/flume_bjxd04.201501150900.1421283612463.log";
		
//		String dirpath  = "/usr/local/flume/AllLog/20150115/";
//		String filepath = "/usr/local/flume/AllLog/20150115/flume_bjxd04.201501150900.1421283612463.log";

		File dirFile = new File(dirpath);
		// 创建文件夹
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File f = new File(filepath);
/*		// 创建文件
		if (!f.exists()) {
			try {
				f.createNewFile();
//				f.createTempFile("kkk2", ".java", dirFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		return f;
	}
		
}
