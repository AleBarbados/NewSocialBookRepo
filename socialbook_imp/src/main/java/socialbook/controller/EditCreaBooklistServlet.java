package socialbook.controller;

import socialbook.Utility.Utility;
import socialbook.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@MultipartConfig
@WebServlet("/EditCreaBooklistServlet")
public class EditCreaBooklistServlet extends HttpServlet {
    private final BookListDAO bookListDAO = new BookListDAO();
    private final BookDAO bookDAO = new BookDAO();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        BookList booklist = new BookList();
        if(request.getParameter("edit")!=null){
            booklist = bookListDAO.doRetriveBooklist(Integer.parseInt(request.getParameter("id")));
            if(request.getParameter("image")!=null) {
                String fileName = Utility.aggiuntaFoto(request);
                booklist.setImage(fileName);
            }
            if(request.getParameter("nome")!=null) {
                booklist.setName(request.getParameter("nome"));
            }
            bookListDAO.doUpdate(booklist);
        }else if(request.getParameter("Create")!=null){

            if(request.getParameter("image")!=null) {
            booklist.setImage(request.getParameter("image"));
            }
            if(request.getParameter("nome")!=null) {
                booklist.setName(request.getParameter("nome"));
            }
            bookListDAO.doSave(booklist, customer.getId_customer());
        }
        ArrayList<Book> books = bookDAO.doRetrieveAll();
        request.setAttribute("books", books);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/libri.jsp");
        requestDispatcher.forward(request, response);
    }
}
