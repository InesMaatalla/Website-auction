package fr.eni.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/accueil")
public class AccueilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String encoding = req.getCharacterEncoding();
        if (encoding == null || !encoding.equalsIgnoreCase("UTF-8")) {
            try {
                req.setCharacterEncoding("UTF-8");
            } catch (Exception e) {
            }
        }

        req.getRequestDispatcher("WEB-INF/accueil.jsp").forward(req,resp);

    }

}
