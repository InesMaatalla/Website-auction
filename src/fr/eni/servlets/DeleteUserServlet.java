package fr.eni.servlets;

import fr.eni.bll.EnchereManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EnchereManager enchereManager = new EnchereManager();
        enchereManager.deleteUser(Integer.parseInt(req.getParameter("delete")));
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("user");

            req.getRequestDispatcher("WEB-INF/liste-encheres.jsp").forward(req, resp);
            //resp.sendRedirect("liste-encheres");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("cretin");
        super.doPost(req, resp);
    }
}
