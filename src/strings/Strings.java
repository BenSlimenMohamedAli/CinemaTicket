/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strings;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author dslim
 */
public class Strings {
    
    public static String getError(String errorName){
        String s = errorName+"[ Error String not Found]";
        try {
            File fXmlFile = new File(Paths.get(".").toAbsolutePath().normalize().toString()+"/src/strings/errors.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            
            doc.getDocumentElement().normalize();
            
            NodeList nList = doc.getElementsByTagName("string");
            
            
            for (int temp = 0; temp < nList.getLength(); temp++) {

		Node nNode = nList.item(temp);
				
		if (nNode.getNodeType() == Node.ELEMENT_NODE ) {

			Element eElement = (Element) nNode;
                        
                        if(eElement.getAttribute("id").equals(errorName)) {
                            s = eElement.getTextContent();
                        }     
		}
	}
            
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return s;
    }
}
