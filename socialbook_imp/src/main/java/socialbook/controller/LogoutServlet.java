package socialbook.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout-servlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getSession().getAttribute("admin")!= null){
            req.getSession().removeAttribute("admin");
        }
        else{

            req.getSession().removeAttribute("personalCustomer");
        }
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        rd.forward(req,resp);
    }
}