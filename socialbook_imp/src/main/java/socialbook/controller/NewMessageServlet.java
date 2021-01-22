package socialbook.controller;

import socialbook.model.Message;
import socialbook.model.MessageDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/new-message-servlet")
public class NewMessageServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean sender;
        MessageDAO messageDAO = new MessageDAO();
        if(request.getSession().getAttribute("admin") != null){
            sender = true;
        }else if(request.getSession().getAttribute("personalCustomer") != null ){
            sender = false;
        }
        messageDAO.doSave(new Message());
    }

}
