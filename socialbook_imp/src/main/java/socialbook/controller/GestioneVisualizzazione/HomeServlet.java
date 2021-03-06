package socialbook.controller.GestioneVisualizzazione;

import socialbook.model.GestioneDatabase.BookDAO;
import socialbook.model.GestioneDatabase.CustomerDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="HomeServlet", urlPatterns = "/index.html", loadOnStartup=1)
public class HomeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDAO customerDAO = new CustomerDAO();
        request.getSession().setAttribute("utentiHome", customerDAO.doRetrieveAll());   //setto tutti gli utenti

        BookDAO bookDAO = new BookDAO();
        request.getSession().setAttribute("libriHome", bookDAO.doRetrieveAll());  //setto tutti i libri

        String address= "/WEB-INF/jsp/index.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}