import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadXML {

	public static void main(String[] args) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read("./etc/config.xml");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = document.getRootElement();

		String a = root.element("a").getText();
		String c = root.element("c").getText();
		System.out.println("a=" + a + "  c=" + c);

		/*
				 //对形如<nodes>192.168.0.202:27121;192.168.0.230:27121</nodes>的格式进行解析
				List<String> mongoNodes = new ArrayList<String>();
				String[] nodes = root.elementTextTrim("nodes").split(";");
				Arrays.stream(nodes).forEach(node -> mongoNodes.add(node));

		 */
		
		
		

		//		@SuppressWarnings("unchecked")
		//		Element cs = root.element("c");
		//		List<Element> ds = cs.elements("d");
		//		for(Element d : ds){
		//			String text = d.getText();
		//			System.out.println(text);
		//		}
	}

}

