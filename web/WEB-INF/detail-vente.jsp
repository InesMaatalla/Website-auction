<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="fr.eni.Utils.Constants" %>
<%@ page import="java.io.File" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Détail vente"/>
</jsp:include>

<body>
<h1 id="titreDetailVente">Détail vente</h1>

<%--<jsp:useBean id="article" scope="request"--%>
<%--             type="fr.eni.bo.Article"/>--%>

<div class="vente-container">
    <div class="vente_body">
        <div class="col-container-vente">
            <div class="col-1-vente">
                <div class="image-vente" id="img">
                    <a href="${pageContext.request.contextPath.concat(File.separator).concat(Constants.UPLOAD_DIRECTORY).concat(File.separator).concat(article.imageName)}" class="zoombox">
                        <img src="${pageContext.request.contextPath.concat(File.separator).concat(Constants.UPLOAD_DIRECTORY).concat(File.separator).concat(article.imageName)}" alt="Image Article">
                    </a>

                </div>
            </div>

            <div class="col-2-vente">
                <table>
                    <tr>
                        <td class="vente-col-1"><p class="vente-nom">Nom :</p></td>
                        <td class="vente-col-2">
                            <div class="rectangle"><p class="vente-resp">${article.nomArticle}</p></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="vente-col-1"><p class="vente-nom">Description :</p></td>
                        <td class="vente-col-2">
                            <div class="rectangle"><p class="vente-resp">${article.description}</p></div>
                        </td>

                    </tr>

                    <tr>
                        <td class="vente-col-1"><p class="vente-nom">Catégorie :</p></td>
                        <td class="vente-col-2">
                            <div class="rectangle"><p class="vente-resp">${article.categorie.libelle}</p></div>
                        </td>

                    </tr>
                    <c:if test="${isLooser != true && isWinner != true && isPasVendu != true}">
                    <c:if test="${article.prixVente < article.prixInitial  && article.comparaisonDateFin <= 0 && article.comparaisonDateDebut >= 0 || article.prixVente > article.prixInitial && article.comparaisonDateDebut >= 0}">
                    <tr>
                        <td class="vente-col-1"><p class="vente-nom">Meilleur offre :</p></td>
                        <td class="vente-col-2">
                            <div class="rectangle"><p class="vente-resp">
                                <c:if test="${article.prixVente < article.prixInitial}">
                                    Aucune offre
                                </c:if>
                                <c:if test="${article.prixVente > article.prixInitial}">
                                    ${article.prixVente} points par ${enchere.pseudoUtilisateur}</p>
                                </c:if>
                                </div>
                        </td>

                    </tr>
                    </c:if>
                    </c:if>
                    <tr>
                        <td class="vente-col-1"><p class="vente-nom">Mise à prix :</p></td>
                        <td class="vente-col-2">
                            <div class="rectangle"><p class="vente-resp">${article.prixInitial} points</p></div>
                        </td>

                    </tr>
                    <c:if test="${isLooser != true && isWinner != true && isPasVendu != true}">
                    <tr>
                        <td class="vente-col-1"><p class="vente-nom">Fin de l'enchère :</p></td>
                        <td class="vente-col-2">
                            <div class="rectangle"><p class="vente-resp">${article.dateFinFormat}</p></div>
                        </td>

                    </tr>
                    </c:if>
                    <tr>
                        <td class="vente-col-1"><p class="vente-nom">Retrait :</p></td>
                        <td class="vente-col-2">
                            <div class="rectangle"><p
                                    class="vente-resp">${retrait.adresse.rue}
                                <br>
                                ${retrait.adresse.codePostal} ${retrait.adresse.ville}</p></div>
                        </td>

                    </tr>

                    <tr>
                        <td class="vente-col-1"><p class="vente-nom">Vendeur :</p></td>
                        <td class="vente-col-2">
                            <div class="rectangle"><p class="vente-resp">${article.utilisateur.pseudo}</p>
                            </div>
                        </td>
                    </tr>

                </table>

                <c:if test="${article.comparaisonDateDebut < 0}">
                    <p id="messageDebutEnchere"><i class="fas fa-hourglass-half icon"></i> L'enchère débute le ${article.dateDebutFormat} <i class="fas fa-hourglass-half icon"></i></p>
                </c:if>

                <c:if test="${isWinner == true}">
                    <p id="messageWinner"><i class="fas fa-gift icon"></i> ${messageVictoire} <i class="fas fa-gift icon"></i><br> Le ${article.dateFinFormat} avec ${article.prixVente} points.</p>
                </c:if>

                <c:if test="${isLooser == true}">
                    <p id="messageLooser"><i class="fas fa-frown icon"></i> ${messageLooser} ${enchere.pseudoUtilisateur} <i class="fas fa-frown icon"></i><br> Le ${article.dateFinFormat} avec ${article.prixVente} points.</p>
                </c:if>

                <c:if test="${isPasVendu == true}">
                    <p id="messagePasVendu"><i class="fas fa-times icon"></i> Enchère terminée le ${article.dateFinFormat} <i class="fas fa-times icon"></i> <br> ${messagePasVendu}</p>
                </c:if>

                <c:if test="${isWinner != true}">
                <c:if test="${article.prixVente < article.prixInitial  && article.comparaisonDateFin <= 0 && article.comparaisonDateDebut >= 0 || article.prixVente > article.prixInitial && article.comparaisonDateDebut >= 0 && article.comparaisonDateFin < 0}">

                <form action="<%=request.getContextPath()%>/encherir" method="post" id="formEnchere">
                    <input type="hidden" name="idArticle" value="${param.idArticle}">
                    <div class="proposition_body">
                        <p id="pointDispo">${pointDispo}</p>
                        <label for="montantPropose" id="maProposition">Ma Proposition :</label>
                        <input class="proposition" type="number" name="montantPropose" id="montantPropose" step="1" value="${valueProposition+1}" required>
                        <input class="input-btn" type="submit" value="Encherir" name="encherir" id="encherir" onclick="document.forms['formEnchere'].submit();">
                        <c:if test="${!empty msg1}">
                            <p class="messageValidationEnchere"><i class="fas fa-check-square iconCheckEnchere"></i> ${msg1}</p>
                        </c:if>
                        <c:if test="${!empty msg2}">
                            <p class="messageErreurEnchere">${msg2}</p>
                        </c:if>
                        <c:if test="${!empty msg3}">
                            <p class="messageErreurEnchere">${msg3}</p>
                        </c:if>
                        <c:if test="${!empty msg4}">
                            <p class="messageErreurEnchere">${msg4}</p>
                        </c:if>
                        <c:if test="${!empty msg5}">
                            <p class="messageErreurEnchere">${msg5}</p>
                        </c:if>
                    </div>

                </form>
                    </c:if>
                </c:if>
            </div>

        </div>




    </div>

</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
