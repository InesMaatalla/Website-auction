package fr.eni.Utils;

import fr.eni.bo.Adresse;
import fr.eni.bo.Utilisateur;

import javax.servlet.http.HttpServletRequest;

public class EncheresService {


//
//
//    public String createToken(Email mail ) {
//
//        Claims claims = Jwts.claims().setSubject( String.valueOf( mail.getId() ) );
//        claims.put( "mailId", mail.getId() );
//        Date currentTime = new Date();
//        currentTime.setTime( currentTime.getTime() + tokenExpiration * 60000 );
//        return Jwts.builder()
//                .setClaims( claims )
//                .setExpiration( currentTime )
//                .signWith( SignatureAlgorithm.HS512, salt.getBytes() )
//                .compact();
//    }
//    public String readMailIdFromToken( String token ) {
//
//        Jwts.parser().setSigningKey( salt.getBytes() ).parseClaimsJws( token ).getSignature();
//        Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey( salt.getBytes() ).parseClaimsJws( token );
//        return parseClaimsJws.getBody().getSubject();
//    }

    public static void Encodage(HttpServletRequest req) {
        String encoding = req.getCharacterEncoding();
        if (encoding == null || !encoding.equalsIgnoreCase("UTF-8")) {
            try {
                req.setCharacterEncoding("UTF-8");
            } catch (Exception e) {
            }
        }
    }


    public static Utilisateur getUserInSession(HttpServletRequest request) {
        return (Utilisateur) request.getSession(false).getAttribute("user");
    }

    /**
     * Création d'un utilisateur à partir de la requete
     * @param request HttpServletRequest
     * @return Utilisateur
     */
    public static Utilisateur getUserFromRequest(HttpServletRequest request) {

        Utilisateur user = new Utilisateur();
        Adresse adress = new Adresse();

        if(request.getParameter("idu") != null ){
            user.setNoUtilisateur(Integer.parseInt(request.getParameter("idu")));
        }
        user.setPseudo(request.getParameter("pseudo"));
        user.setNom(request.getParameter("nom"));
        user.setPrenom(request.getParameter("prenom"));
        user.setEmail(request.getParameter("email"));
        user.setTelephone(request.getParameter("telephone"));
        user.setMotDePasse(request.getParameter("motDePasse"));
        if(request.getParameter("ida")!= null){
            adress.setId(Integer.parseInt(request.getParameter("ida")));
        }
        adress.setRue(request.getParameter("rue"));
        adress.setCodePostal(request.getParameter("codePostal"));
        adress.setVille(request.getParameter("ville"));

        user.setAdresse(adress);

        return user;

    }

}
