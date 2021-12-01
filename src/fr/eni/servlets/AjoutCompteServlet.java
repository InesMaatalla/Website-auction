package fr.eni.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ajout-compte")
public class AjoutCompteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String encoding = req.getCharacterEncoding();
        if (encoding == null || !encoding.equalsIgnoreCase("UTF-8")) {
            try {
                req.setCharacterEncoding("UTF-8");
            } catch (Exception e) {
            }
        }

        req.getRequestDispatcher("WEB-INF/ajout-compte.jsp").forward(req,resp);

    }

}
