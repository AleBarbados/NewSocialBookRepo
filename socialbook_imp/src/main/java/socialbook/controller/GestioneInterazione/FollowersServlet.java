package socialbook.controller.GestioneInterazione;

import socialbook.model.GestioneDatabase.Follow;
import socialbook.model.GestioneDatabase.FollowDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/FollowersServlet")
public class FollowersServlet extends HttpServlet {
    private final FollowDAO followDAO = new FollowDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Follow> follows = new ArrayList<>();

        if(request.getParameter("Following")!=null){
            follows=followDAO.doRetriveAllFollowed(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("Following", "Following");
        }else if(request.getParameter("Followers")!=null){
            follows=followDAO.doRetriveAllFollowers(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("Followers", "Followers");
        }
        request.setAttribute("follows", follows);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("WEB-INF/jsp/followers.jsp");
        requestDispatcher.forward(request, response);
    }
}
