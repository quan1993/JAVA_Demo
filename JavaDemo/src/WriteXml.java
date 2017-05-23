

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class WriteXml {

	public static void main(String[] args) {
		
		/**
		 * 将数据写入到xml文件中
		 */
		
		
		String xmlStr = "<resource><directory>deploy/${env}/image</directory><targetPath>statics/themes/default/images</targetPath></resource>";
        WriteXml.writeXmlStr(xmlStr, new File("ss.xml"));
	}
	
	public static void writeXmlStr(String xmlStr, File file){
		SAXReader reader = new SAXReader();
		Document doc = null;
		
		try {
			doc = reader.read(new ByteArrayInputStream(xmlStr.getBytes()));
			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer = new XMLWriter(new FileWriter(file), format);
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
