import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.xml.transform.stream.StreamResult;

public class AddProgrammerServlet extends HttpServlet {
    enum Phases {name,langs,days,focus,finish;}
    XmlStore xml;
    @Override
    public void init() throws ServletException {
        super.init();
        xml = XmlStore.getInstance();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Enumeration<String> params = req.getParameterNames();
        while (params.hasMoreElements()){
            String param = params.nextElement();
            switch (param){
                case "fName":
                    session.setAttribute("fName",req.getParameter("fName"));
                    break;
                case "lName":
                    session.setAttribute("lName",req.getParameter("lName"));
                    break;
                case "langs":
                    session.setAttribute("langs",req.getParameter("langs"));
                    break;
                case "days":
                    session.setAttribute("days",req.getParameter("days"));
                    break;
                case "focus":
                    session.setAttribute("focus",req.getParameter("focus"));
                    break;
            }
        }
        String path = req.getRequestURI().substring(14);
        RequestDispatcher dispatcher;
        String jsps = "/WEB-INF/JSPs/";
        switch (path){
            case "":
                resp.sendRedirect("/newprogrammer/name");
                /*dispatcher=req.getRequestDispatcher(jsps+"name.jsp");
                resp.setStatus(HttpServletResponse.SC_OK);
                dispatcher.forward(req,resp);*/
                break;
            case "/name":
                dispatcher=req.getRequestDispatcher(jsps+"name.jsp");
                resp.setStatus(HttpServletResponse.SC_OK);
                dispatcher.forward(req,resp);
                break;
            case "/languages":
                dispatcher=req.getRequestDispatcher(jsps+"langs.jsp");
                resp.setStatus(HttpServletResponse.SC_OK);
                dispatcher.forward(req,resp);
                break;
            case "/days":
                dispatcher=req.getRequestDispatcher(jsps+"days.jsp");
                resp.setStatus(HttpServletResponse.SC_OK);
                dispatcher.forward(req,resp);
                break;
            case "/focus":
                dispatcher=req.getRequestDispatcher(jsps+"focus.jsp");
                resp.setStatus(HttpServletResponse.SC_OK);
                dispatcher.forward(req,resp);
                break;
            case "/submit":
                dispatcher=req.getRequestDispatcher(jsps+"submit.jsp");
                resp.setStatus(HttpServletResponse.SC_OK);
                dispatcher.forward(req,resp);
                break;
            case "/cancel":
                session.invalidate();
                resp.sendRedirect(req.getContextPath()+"/");
                break;
            case "/add":
                try {
                    String fname = (String) session.getAttribute("fName");
                    String lname = (String) session.getAttribute("lName");
                    System.out.println("names retrieved");
                    String[] langs = ((String) session.getAttribute("langs")).split(",");
                    String[] days = ((String) session.getAttribute("days")).split(",");
                    String focus = (String) session.getAttribute("focus");
                    System.out.println("attrs retrieved");
                    String result = addProgrammer(fname, lname, langs, days, focus);
                    req.setAttribute("result", result);
                    req.setAttribute("count", xml.getCount());
                    dispatcher = getServletContext().getRequestDispatcher(jsps + "programmerAdded.jsp");
                    resp.setStatus(HttpServletResponse.SC_OK);
                    dispatcher.forward(req, resp);
                }catch (Exception e){
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST,"The session does not have enough information to add a new programmer");
                }
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"This page does not exist");
        }
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
