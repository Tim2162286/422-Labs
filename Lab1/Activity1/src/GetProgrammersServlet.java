import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class GetProgrammersServlet extends HttpServlet {
    XmlStore xml;
    @Override
    public void init() throws ServletException {
        super.init();
        xml = XmlStore.getInstance();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Node programmers = xml.getDoc().getElementsByTagName("programmers").item(0);
        resp.setStatus(HttpServletResponse.SC_OK);
        String userAgent = req.getHeader("User-Agent");
        StreamResult out = new StreamResult(resp.getWriter());
        DocumentTraversal traversal = (DocumentTraversal) xml.getDoc();
        Map<String,String[]> params = req.getParameterMap();
        ProgrammerFilter filter = new ProgrammerFilter();
        filter.setParams(params);
        NodeIterator iterator = traversal.createNodeIterator(programmers,
                NodeFilter.SHOW_ALL, filter, true);
        resp.setStatus(HttpServletResponse.SC_OK);
        if(userAgent.contains("Chrome"))
            resp.getWriter().println("Welcome Chrome User");
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            doc.appendChild(doc.createElement("programmers"));
            Element root = doc.getDocumentElement();
            for (Node n = iterator.nextNode(); n != null; n = iterator.nextNode()) {
                root.appendChild(doc.importNode(n,true));
            }
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(doc),out);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"/programmers should be accessed by GET");
    }
}
