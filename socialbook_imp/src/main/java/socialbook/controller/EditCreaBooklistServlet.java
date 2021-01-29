package socialbook.controller;

import socialbook.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if(customer == null){
            throw new socialbook.controller.ServletException("HEYY, devi fare l'accesso prima!!");
        }

        BookList booklist = new BookList();
        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");

        if(idStr != null) {         //modifica booklist
            booklist = bookListDAO.doRetriveBooklist(Integer.parseInt(idStr));
        }

        if(nome != null)
            booklist.setName(nome);

        String fileName = request.getParameter("foto");
        if(fileName != null)
            booklist.setImage(fileName);
        else
            booklist.setImage("bl0.jpg");

        booklist.setFavorite(false);

        if(request.getParameter("edit") != null)
            bookListDAO.doUpdate(booklist);
        else
            bookListDAO.doSave(booklist, customer.getId_customer());


        /*BookList booklist = new BookList();
        if(request.getParameter("edit")!=null){

            if(request.getParameter("foto")!=null) {
                String fileName = Utility.aggiuntaFoto(request);
                booklist.setImage(fileName);
            }
            if(request.getParameter("nome")!=null) {
                booklist.setName(request.getParameter("nome"));
            }
            bookListDAO.doUpdate(booklist);
        }else if(request.getParameter("Create")!=null){

            if(request.getParameter("foto")!=null) {
                String fileName = Utility.aggiuntaFoto(request);
                booklist.setImage(fileName);
            }
            if(request.getParameter("nome")!=null) {
                booklist.setName(request.getParameter("nome"));
            }
            bookListDAO.doSave(booklist, customer.getId_customer());
        }
        ArrayList<Book> books = bookDAO.doRetrieveAll();
        request.setAttribute("books", books); */

        /*request.setAttribute("booklists", bookListDAO.doRetriveFromCustomer(customer.getId_customer()));
        request.setAttribute("followed", bookListDAO.doRetriveFollowed(customer.getId_customer()));*/

        /*RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/index.jsp");
        dispatcher.forward(request, response);*/

        response.sendRedirect("allBooklistServlet?view=true");
    }
}
