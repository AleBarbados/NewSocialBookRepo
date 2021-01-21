package socialbook.controller;

import socialbook.model.BookList;
import socialbook.model.BookListDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "BooklistViewServlet")
public class BooklistViewServlet extends HttpServlet {
    private final BookListDAO bookListDAO = new BookListDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<BookList> booklists = bookListDAO.doRetriveFromCustomer(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("booklists", booklists);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/allBooklistsView.jsp");
        requestDispatcher.forward(request, response);
    }
}
