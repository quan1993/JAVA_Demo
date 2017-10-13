package flume;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class FilePathManager {
	private long seriesTimestamp;
	  private String baseDirectory;
	  private AtomicInteger fileIndex;

	  private File currentFile;
	 
	  private String pefix; 
	  private String suffix; 
	  
	  
	  public FilePathManager() {
	    seriesTimestamp = System.currentTimeMillis();
	    fileIndex = new AtomicInteger();
	  }

	  
	  public File nextFile() {
	       //(1)  /usr/local/flume/xxxxpjmLog/%Y%m%d 将%Y%m%d替换为年月日 并返回(此处为省事整串替换，配置文件中的也必须写成%Y%m%d<span style="font-family: Arial, Helvetica, sans-serif;">)</span>
	       String dirStr = FileSinkDefinedUtils.getRealPath(baseDirectory);
	       //(2)  flume_bjxd02.%Y%m%d%H%M将%Y%m%d%H%M替换为年月日时分
	       String pefixStr = FileSinkDefinedUtils.getRealPathFilePrefix(pefix);
	       //(3)  拼文件全路径/data/logs/flume/allpjm/20150115/flume_bjxd02.201501151029.1421288975655.log 
	       //    （写文件中需要添加.tmp后缀）
	       String filePath = dirStr+pefixStr+"."+System.currentTimeMillis()+suffix+".tmp";
		   currentFile = FileSinkDefinedUtils.CreateFolderAndFile(dirStr, filePath);
		   
		   return currentFile;
		}
	 /* public File nextFile() {
	    currentFile = new File(baseDirectory, seriesTimestamp + "-"
	        + fileIndex.incrementAndGet());

	    return currentFile;
	  }
	*/
	  public File getCurrentFile() {
	    if (currentFile == null) {
	      return nextFile();
	    }

	    return currentFile;
	  }

	  public void rotate() {
	    currentFile = null;
	  }

	  public String getBaseDirectory() {
	    return baseDirectory;
	  }

	  public void setBaseDirectory(String baseDirectory) {
	    this.baseDirectory = baseDirectory;
	  }

	  public long getSeriesTimestamp() {
	    return seriesTimestamp;
	  }

	  public AtomicInteger getFileIndex() {
	    return fileIndex;
	  }


	public String getPefix() {
		return pefix;
	}


	public void setPefix(String pefix) {
		this.pefix = pefix;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
