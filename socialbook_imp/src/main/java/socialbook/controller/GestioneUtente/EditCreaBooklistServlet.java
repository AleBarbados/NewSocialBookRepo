package socialbook.controller.GestioneUtente;

import socialbook.model.GestioneDatabase.BookList;
import socialbook.model.GestioneDatabase.BookListDAO;
import socialbook.model.GestioneDatabase.Customer;

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = (Customer) request.getSession().getAttribute("personalCustomer");
        if(customer == null){
            throw new socialbook.utility.ServletException("HEYY, devi fare l'accesso prima!!");
        }

        BookList booklist = new BookList();
        String idBooklist = request.getParameter("id");
        String name = request.getParameter("nome");

        if(idBooklist != null) {         //modifica booklist
            booklist = bookListDAO.doRetriveBooklist(Integer.parseInt(idBooklist));
        }

        if(name != null)
            booklist.setName(name);



        booklist.setFavorite(false);

        if(request.getParameter("edit") != null)
            bookListDAO.doUpdate(booklist);
        else {
            String fileName = request.getParameter("foto");
            if (fileName != null)
                booklist.setImage(fileName);
            else
                booklist.setImage("bl0.jpg");       //se l'utente non sceglie nessuna immagine, verrà salvata una predefinita
            bookListDAO.doSave(booklist, customer.getId_customer());
        }
        response.sendRedirect("allBooklistServlet?id="+customer.getId_customer());
    }
}
