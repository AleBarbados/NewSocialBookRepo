package socialbook.controller;

import socialbook.Utility.Utility;
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

@WebServlet("/followEdit")
public class FollowEditServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final FollowDAO followDAO = new FollowDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dest, conf;
        RequestDispatcher dispatcher;

        if (request.getParameter("follow") != null) {
            Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
            followDAO.doFollow(Integer.parseInt(request.getParameter("id")), customer.getId_customer());

            dest = request.getHeader("referer");
            conf = "/customerServlet";
            Utility.redirect(response, dest, conf);

        } else if (request.getParameter("unFollow") != null) {
            Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
            followDAO.doDelete(customer.getId_customer(), Integer.parseInt(request.getParameter("id")));

            dest = request.getHeader("referer");
            conf = "/customerServlet";
            Utility.redirect(response, dest, conf);

        } else {
            if (request.getParameter("editProfile").equals("edit")) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/customerEdit.jsp");
            } else {
                Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");

                customer.setC_pwd(Utility.encryptionSHA1(request.getParameter("password")));
                customer.setDescription(request.getParameter("descrizione"));
                String fileName = Utility.aggiuntaFoto(request);
                customer.setImage(fileName);

                customerDAO.doUpdate(customer);

                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/customerView.jsp");
            }
            dispatcher.forward(request, response);
        }
    }
}
