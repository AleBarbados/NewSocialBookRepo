package socialbook.controller.GestioneInterazione;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        String destination;

        if(customer == null)
            throw new socialbook.controller.ServletException("Bisogna prima effettuare l'accesso!!");


        if (request.getParameter("follow") != null) {
            followDAO.doFollow(Integer.parseInt(request.getParameter("id")), customer.getId_customer());

            destination = request.getHeader("referer");
            response.sendRedirect(destination);

        } else if (request.getParameter("unFollow") != null) {
            followDAO.doDelete(customer.getId_customer(), Integer.parseInt(request.getParameter("id")));

            destination = request.getHeader("referer");
            response.sendRedirect(destination);

        } else {
            if (request.getParameter("editProfile").equals("edit")) {
                dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/customerEdit.jsp");
            } else {
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
