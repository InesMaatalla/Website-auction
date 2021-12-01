package fr.eni.servlets;

import fr.eni.Utils.EmailUtility;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/resetPassword")
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String host;
    private String port;
    private String email;
    private String name;
    private String pass;

    @Override
    public void init() {
        // Récuperation de la conf SMTP depuis le web.xml
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        email = context.getInitParameter("email");
        name = context.getInitParameter("name");
        pass = context.getInitParameter("pass");
    }


    public ResetPasswordServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        String page = "WEB-INF/resetPassword.jsp";
        request.getRequestDispatcher(page).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String recipient = request.getParameter("email");
        String subject = "Demande de réinitialisation de mot de passe";



        String content = "Bonjour, Veuillez cliquer sur le lien suivant pour réinitialiser votre mot de passe : ";
        //String url = "http://" + request.getServletPath() + ""

        String message = "";

        try {
            EmailUtility.sendEmail(host, port, email, name, pass,recipient, subject, content);
            message = "Pour réinitialiser votre mot de passe, va voir tes mails.";
        } catch (Exception ex) {
            ex.printStackTrace();
            message = "There were an error: " + ex.getMessage();
        } finally {
            request.setAttribute("message", message);
            request.getRequestDispatcher("message.jsp").forward(request, response);
        }
    }

}