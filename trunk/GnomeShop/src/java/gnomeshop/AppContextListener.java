/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gnomeshop;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author Kop
 */
public class AppContextListener implements ServletContextListener{

    public void contextInitialized(ServletContextEvent event) {
        // Initilize and set the attribute of database
        ServletContext servletContext = event.getServletContext();
        DatabaseUtil dbUtil = new DatabaseUtil();
        servletContext.setAttribute("DATABASE_UTIL", dbUtil);
    }

    public void contextDestroyed(ServletContextEvent event) {
        // Remove the binding of database and the attribute
        ServletContext servletContext = event.getServletContext();
        servletContext.removeAttribute("DATABASE_UTIL");
    }
}
