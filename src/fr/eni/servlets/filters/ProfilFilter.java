package fr.eni.servlets.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Ce filtre permet de modifier le comportement des boutons présents sur la page du profil
 */
@WebFilter("/profil")
public class ProfilFilter implements Filter {

    public ProfilFilter() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if("inscription".equals(servletRequest.getParameter("from"))) {
            //on arrive du lien s'inscrire donc btn = creer
            servletRequest.setAttribute("btnValidateProfile", "Créer");
            servletRequest.setAttribute("status", "create");
        } else {
            servletRequest.setAttribute("btnValidateProfile", "Modifier");
            servletRequest.setAttribute("status", "update");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
