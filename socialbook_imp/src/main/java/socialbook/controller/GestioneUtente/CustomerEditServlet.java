package socialbook.controller.GestioneUtente;

import socialbook.utility.Utility;
import socialbook.model.GestioneDatabase.Customer;
import socialbook.model.GestioneDatabase.CustomerDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet("/customerEdit")
public class CustomerEditServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");

        if(customer == null)
            throw new socialbook.utility.ServletException("Bisogna prima effettuare l'accesso!!");

        String fileName = request.getParameter("foto");

        customer.setC_pwd(Utility.encryptionSHA1(request.getParameter("password")));
        customer.setDescription(request.getParameter("descrizione"));
        customer.setImage(fileName);

        customerDAO.doUpdate(customer);

        request.setAttribute("idCustomer", customer.getId_customer());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/customerView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}
