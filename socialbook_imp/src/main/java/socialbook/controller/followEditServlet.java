package socialbook.controller;

import socialbook.model.Customer;
import socialbook.model.CustomerDAO;
import socialbook.model.FollowDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "followEditServlet")
public class followEditServlet extends HttpServlet {
    private static CustomerDAO customerDAO = new CustomerDAO();
    private static FollowDAO followDAO = new FollowDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("follow")!=null){
            Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
            followDAO.doFollow(customer.getId_customer(), (Integer) request.getAttribute("id"));
            String dest = request.getHeader("referer");     //prendiamo dall'header della richiesta l'url corrente
            if(dest == null || dest.contains("/CustomerServlet") || dest.trim().isEmpty()){
                dest = ".";     //la destinazione sarà la pagina corrente
            }
            request.setAttribute("redirect", true);
            request.setAttribute("customer", customerDAO.doRetriveById((Integer) request.getAttribute("id")));
            response.sendRedirect(dest);
        }else if(request.getParameter("edit")!=null){
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/customerEdit.jsp");
            requestDispatcher.forward(request, response);
        }else if(request.getParameter("unFollow")!=null){
            Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
            followDAO.doDelete(customer.getId_customer(), (Integer) request.getAttribute("id"));
            String dest = request.getHeader("referer");     //prendiamo dall'header della richiesta l'url corrente
            if(dest == null || dest.contains("/CustomerServlet") || dest.trim().isEmpty()){
                dest = ".";     //la destinazione sarà la pagina corrente
            }
            request.setAttribute("follow", false);
            request.setAttribute("redirect", true);
            request.setAttribute("customer", customerDAO.doRetriveById((Integer) request.getAttribute("id")));
            response.sendRedirect(dest);
        }
    }
}
