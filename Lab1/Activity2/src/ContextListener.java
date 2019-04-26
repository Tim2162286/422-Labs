import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String path = servletContextEvent.getServletContext().getRealPath("/");
        System.out.println(path);
        XmlStore.createInstance(path);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
