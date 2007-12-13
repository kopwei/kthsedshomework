/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.*;
import java.net.*;

import javax.ejb.EJB;
import currencyconvert.CurrencyTransferLocal;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author kop
 */
public class FrontController extends HttpServlet {
    @EJB CurrencyTransferLocal translator;
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String message = null;
        String originalCurrency = request.getParameter("OriginalCurrency");
        String targetCurrency = request.getParameter("TargetCurrency");
        String valueStr = request.getParameter("Value");
        
        
        if (null == originalCurrency || null == targetCurrency || null == valueStr || null == translator) {
            request.getRequestDispatcher("account.jsp").forward(request, response);            
        }   
        try {
            float inputValue = Float.parseFloat(valueStr);
            float targetValue = translator.transfer(originalCurrency, targetCurrency, inputValue);
            message = "The value of target currency is " + targetValue;
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FrontController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FrontController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } catch (Exception e) {
            message = e.toString();
            e.printStackTrace();
        } 
        request.setAttribute("message", message);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
    * Returns a short description of the servlet.
    */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
