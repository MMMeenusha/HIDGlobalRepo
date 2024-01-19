package Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLReader {
	
	File xmlfile;
	SAXBuilder saxbuilder;
	Document document;
	Element rootelement, actualelement;
	List<Element> childelements;
	
	public void loadTestDataXML(String file) throws JDOMException, IOException{
		xmlfile = new File(file);
		saxbuilder= new SAXBuilder();
		document = saxbuilder.build(xmlfile);
		rootelement = document.getRootElement();
		childelements = rootelement.getChildren();
	}
	
	public void getTCNameFromChildren(String TCName){
		for(Element element : childelements){
			String attrvalue = element.getAttribute("name").getValue().trim();
			if(attrvalue.contains(TCName)){
				actualelement=element;
				break;
			}
		}
	}
	
	public String getText(String node){
		String nodevalue =  actualelement.getChild(node).getText();
		return nodevalue;
	}
	
	public void loadXML(String xmlName,String mName) throws Exception
	{
		loadTestDataXML(System.getProperty("user.dir") + "\\src\\test\\resources\\TestData\\XMLData\\"+xmlName+".xml");
		getTCNameFromChildren(mName);
	}

}
