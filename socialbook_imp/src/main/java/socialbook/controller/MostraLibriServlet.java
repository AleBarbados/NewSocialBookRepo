package socialbook.controller;

import socialbook.model.BookDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/mostraLibriServlet")
public class MostraLibriServlet extends HttpServlet {
    private final BookDAO bookDAO = new BookDAO();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("books", bookDAO.doRetrieveAll());

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/libri.jsp");
        dispatcher.forward(request, response);
    }
}
