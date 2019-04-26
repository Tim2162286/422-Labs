import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import java.io.Console;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilderFactory;

public class AddProgrammerServlet extends HttpServlet {
    XmlStore xml;
    @Override
    public void init() throws ServletException {
        super.init();
        xml = XmlStore.getInstance();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Programmers can only be added using POST");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String first = req.getParameter("firstname");
        String last = req.getParameter("lastname");
        String[] languages = null;
        if(req.getParameter("languages")!=null)
            languages = req.getParameter("languages").split(",");
        String[] days = req.getParameterValues("day");
        String focus = req.getParameter("focus");
        String result;
        if(languages !=null && languages.length>0 && days!= null)
            result = addProgrammer(first,last,languages,days,focus);
        else
            result = "Programmer not added. You must have at least one programming language and at least one day";
        req.setAttribute("result", result);
        req.setAttribute("count",xml.getCount());
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/JSPs/programmerAdded.jsp");
        resp.setStatus(HttpServletResponse.SC_OK);
        dispatcher.forward(req,resp);



    }

    private String addProgrammer(String first, String last, String[] languages, String[] days, String focus){
        try {
            Document doc = xml.getDoc();
            Element programmer = doc.createElement("programmer");
            Node firstName = doc.createElement("firstname");
            firstName.appendChild(doc.createTextNode(first));
            Node lastName = doc.createElement("lastname");
            lastName.appendChild(doc.createTextNode(last));
            Node langs = doc.createElement("languages");
            for (String s : languages) {
                Node lang = doc.createElement("language");
                lang.appendChild(doc.createTextNode(s));
                langs.appendChild(lang);
            }
            Node dayList = doc.createElement("days");
            for (String s : days) {
                Node day = doc.createElement("day");
                day.appendChild(doc.createTextNode(s));
                dayList.appendChild(day);
            }
            Node age = doc.createElement("focus");
            age.appendChild(doc.createTextNode(focus));
            programmer.appendChild(firstName);
            programmer.appendChild(lastName);
            programmer.appendChild(langs);
            programmer.appendChild(dayList);
            programmer.appendChild(age);
            xml.addProgrammer(programmer);
            return "Programmer successful added.";
        } catch (Exception e){
            return "Programmer could not be added, unknown issue.";
        }
    }
}
