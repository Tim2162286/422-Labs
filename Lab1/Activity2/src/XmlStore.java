import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlStore {
    private static XmlStore instance;
    private Document doc;
    private File file;
    private Transformer transform;

    public static XmlStore getInstance(){
        return instance;
    }
    public static void createInstance(String path){
        if(instance == null){
            instance = new XmlStore(path);
        }
    }
    private XmlStore(String path) {
        try {
            file = new File(path+"/WEB-INF/programmers.xml");
            file.createNewFile();
            transform = TransformerFactory.newInstance().newTransformer();
            transform.setOutputProperty(OutputKeys.INDENT,"yes");
            transform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            try {
                doc = docBuilder.parse(file);
            }
            catch (SAXException sE){
                System.out.println("creating new doc");
                doc = docBuilder.newDocument();
                doc.appendChild(doc.createElement("xml"));
            }
            NodeList p = doc.getElementsByTagName("programmers");
            if(p.getLength() == 0){
                doc.getDocumentElement().appendChild(doc.createElement("programmers"));
                writeXML();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void writeXML(){
        StreamResult xmlFile = new StreamResult(file);
        try {
            transform.transform(new DOMSource(doc), xmlFile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addProgrammer(Element programmer){
        Node programmers = doc.getElementsByTagName("programmers").item(0);
        programmers.appendChild(programmer);
        writeXML();
    }
    public Document getDoc(){return doc;}
    public int getCount(){
        Element programmers = (Element) doc.getElementsByTagName("programmers").item(0);
        return programmers.getElementsByTagName("programmer").getLength();
    }
}
