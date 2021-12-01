package fr.eni.servlets;

import fr.eni.Utils.EncheresService;
import fr.eni.bll.EnchereManager;
import fr.eni.bo.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static fr.eni.Utils.Constants.*;

@WebServlet("/ajout-article")
public class AjoutArticleServlet extends HttpServlet {

    EnchereManager em = new EnchereManager();

    RequestDispatcher rd;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EncheresService.Encodage(req);

        String destPage = "WEB-INF/ajout-article.jsp";
        HttpSession s = req.getSession(false);

        List<Categorie> listeCategories = em.selectAllCategories();
        req.setAttribute("listeCategories", listeCategories);
        Utilisateur u = (Utilisateur) s.getAttribute("user");
        int idUtilisateur = u.getNoUtilisateur();
        Adresse a = em.selectAdresseByIdUtilisateur(idUtilisateur);

        req.setAttribute("rue", a.getRue());
        req.setAttribute("codePostal", a.getCodePostal());
        req.setAttribute("ville", a.getVille());

        req.getRequestDispatcher("WEB-INF/ajout-article.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EncheresService.Encodage(req);
        HttpSession session = req.getSession(false);
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        String nomArticle = "";
        String description = "";
        LocalDate dateDebutArticle = null;
        LocalDate dateFinArticle = null;
        int prix = 0;
        int categorie = 0;
        String rueRetrait = "";
        String codePostalRetrait = "";
        String villeRetrait = "";
        String uploadFileName = DEFAULT_UPLOAD_FILENAME;

        if (ServletFileUpload.isMultipartContent(req)) {

            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(MAX_FILE_SIZE);
            upload.setSizeMax(MAX_REQUEST_SIZE);

            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                Boolean b = uploadDir.mkdir();
            }
            try {
                List<FileItem> formItems = upload.parseRequest(req);

                if (formItems != null && formItems.size() > 0) {

                    for (FileItem item : formItems) {
                        if (!item.isFormField() && !"".equals(item.getName())) {
                            // TODO si une exception est levé après la gestion du fichier le fichier est conservé dans repertoire d'upload alors que l'article n'est pas enregistré en bdd
                            uploadFileName = new File(item.getName()).getName();
//                            String uploadFilePath = uploadPath + File.separator + uploadFileName;
                            uploadFileName = "art_" + nomArticle.trim() + "_" + new Date().getTime() + "_" + uploadFileName;
                            File newFile = new File(uploadPath + File.separator + uploadFileName);
                            item.write(newFile);
                        } else {
                            switch (item.getFieldName()) {
                                case "nomArticle" -> nomArticle = item.getString("UTF-8");
                                case "description" -> description = item.getString("UTF-8");
                                case "prixArticle" -> prix = Integer.parseInt(item.getString());
                                case "dateDebutArticle" -> dateDebutArticle = LocalDate.parse(item.getString("UTF-8"));
                                case "dateFinArticle" -> dateFinArticle = LocalDate.parse(item.getString("UTF-8"));
                                case "categories" -> categorie = Integer.parseInt(item.getString("UTF-8"));
                                case "rueRetrait" -> rueRetrait = item.getString("UTF-8");
                                case "villeRetrait" -> villeRetrait = item.getString("UTF-8");
                                case "codePostalRetrait" -> codePostalRetrait = item.getString("UTF-8");
                            }
                        }
                    }
                    //traitement
                    Article article = new Article();
                    article.setNomArticle(nomArticle);
                    article.setDescription(description);
                    article.setDateDebutEncheres(dateDebutArticle);
                    article.setDateFinEncheres(dateFinArticle);
                    article.setPrixInitial(prix);
                    article.setImageName(uploadFileName);
                    em.insertArticle(article, user.getNoUtilisateur(), categorie);

                    int idArticle = article.getNoArticle();

                    Adresse adresseRetrait = new Adresse(rueRetrait, codePostalRetrait, villeRetrait);
                    em.insert(adresseRetrait);

                    int idAdresseRetrait = adresseRetrait.getId();

                    em.insertRetraitById(idArticle, idAdresseRetrait);
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/liste-encheres");
    }
}
