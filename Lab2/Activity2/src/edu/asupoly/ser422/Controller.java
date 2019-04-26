package edu.asupoly.ser422;

import com.mysql.cj.jdbc.MysqlDataSource;
import edu.asupoly.ser422.DAO.PhoneBookDAO;
import edu.asupoly.ser422.DAO.PhoneBookFlatDAO;
import edu.asupoly.ser422.DAO.PhoneBookMySqlDAO;
import edu.asupoly.ser422.model.DataStore;
import edu.asupoly.ser422.model.PhoneEntry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class Controller extends HttpServlet {
    private static Logger log = Logger.getLogger(Controller.class.getName());
    PhoneBookDAO pbook;
    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        String realPath = context.getRealPath("/");
        if(!DataStore.isInitilized()){
            DataStore.createDataStore(realPath);
        }
        pbook = DataStore.getDataStore().getPbook();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       switch (req.getPathInfo()) {
            case "/add":
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Use POST to add a new listing");
                break;
            case "/remove":
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Use POST to remove a listing");
                break;
            case "/list":
                req.setAttribute("listings",pbook.listEntries());
                req.getRequestDispatcher("/WEB-INF/view/list.jsp").forward(req,resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()) {
            case "/add":
                System.out.println("add");
                pbook.addEntry(req.getParameter("firstname"),req.getParameter("lastname"),req.getParameter("phone"));
                req.setAttribute("action","Add");
                req.setAttribute("result","Entry added to phonebook");
                req.getRequestDispatcher("/WEB-INF/view/listingChange.jsp").forward(req,resp);
                break;
            case "/remove":
                PhoneEntry rem = pbook.removeEntry(req.getParameter("phone"));
                req.setAttribute("action","Remove");
                if(rem!=null)
                    req.setAttribute("result","Removed entry "+rem);
                else
                    req.setAttribute("result","No entry with phone number " + req.getParameter("phone"));
                req.getRequestDispatcher("/WEB-INF/view/listingChange.jsp").forward(req,resp);
                break;
            case "/list":
                req.setAttribute("listings",pbook.listEntries());
                req.getRequestDispatcher("/WEB-INF/view/list.jsp").forward(req,resp);
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }


}
