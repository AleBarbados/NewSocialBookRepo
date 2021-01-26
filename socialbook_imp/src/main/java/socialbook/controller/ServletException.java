package socialbook.controller;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "ServletException")
public class ServletException extends javax.servlet.ServletException {
    private static final long serialVersionUID = 1L;

    public ServletException(String message) {
        super(message);
    }
}
