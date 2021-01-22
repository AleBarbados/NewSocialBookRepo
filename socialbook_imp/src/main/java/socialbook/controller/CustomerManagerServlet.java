package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.BookDAO;
import socialbook.model.CustomerDAO;
import socialbook.model.ReviewDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customerManagerServlet")
public class CustomerManagerServlet extends HttpServlet {
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final ReviewDAO reviewDAO = new ReviewDAO();
    private final BookDAO bookDAO = new BookDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String c_id = request.getParameter("id");
        String r_id = request.getParameter("r_id");
        String isbn = request.getParameter("libro");
        String dest, conf;

        if(isbn != null) {      //click sul bottone 'mostra libro' dalla pagina index
            request.setAttribute("libro", bookDAO.doRetrieveByIsbn(isbn));
            //request.setAttribute("recensioni", reviewDAO.doRetrieveByIsbn(isbn));

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/recensione.jsp");
            dispatcher.forward(request, response);
        }

        if(c_id != null) {      //click sul bottone 'rimuovi' associato ad un certo utente
            customerDAO.doDeleteById(Integer.parseInt(c_id));

            dest = request.getHeader("referer");
            conf = "/customerManagerServlet";
            Utility.redirect(response, dest, conf);
        }

        if(r_id != null) {      //click sul bottone per la rimozione di una recensione
            reviewDAO.doDeleteById(Integer.parseInt(r_id));

            dest = request.getHeader("referer");
            conf = "/customerManagerServlet";
            Utility.redirect(response, dest, conf);
        }

        //click sul bottone 'mostra tutti gli utenti' dalla pagina index
        request.setAttribute("customers", customerDAO.doRetrieveAll());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/utenti.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
