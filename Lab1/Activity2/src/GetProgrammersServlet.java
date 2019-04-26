import Util.Search;
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
        String fname = req.getParameter("firstname");
        String lname = req.getParameter("lastname");
        String langs = req.getParameter("languages");
        System.out.println("Test");
        String[] dayList = null;
        String daysStr = "";
        boolean hasDays = req.getParameterMap().containsKey("day");
        if(hasDays)
            dayList = req.getParameterValues("day");
        System.out.println(dayList);
        String days = "";
        if (dayList!=null) {
            System.out.println("Test 3");
            for (String s : dayList) {
                days += s;
            }
        }
        String focus = req.getParameter("focus");
        String querry = "?";
        if(fname.length()>0)
            querry+="firstname="+fname+"&";
        if(lname.length()>0)
            querry+="lastname="+lname+"&";
        if(langs.length()>0)
            querry+="languages="+langs+"&";
        if(days.length()>0)
            querry+="days="+days+"&";
        if(focus.length()>0)
            querry+="focus="+focus+"&";
        querry = querry.substring(0,querry.length()-1);
        Search search = new Search(fname,lname,langs,dayList,focus);
        String cookieStr = search.getCookieString();
        System.out.println(cookieStr);
        Cookie cookie = new Cookie("search",search.getCookieString());
        cookie.setMaxAge(5*60);
        resp.addCookie(cookie);
        resp.sendRedirect("/programmers"+querry);
    }
}
