<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%@ page import="fr.eni.Utils.Constants" %>
<%@ page import="java.io.File" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Liste des enchères"/>
</jsp:include>

<body>

<h1 id="titreListeEncheres">Liste des enchères</h1>

<section id="filtres">
    <form action="liste-encheres" method="post" id="formRecherche">
        <div id="rechercheCat">
            <c:if test="${sessionScope.user != null}">
                <div id="achatsGroupe" class="filtrage">
                    <label class="container" id="containerAchats">Achats
                        <input type="radio" value="true" checked="checked" name="radio">
                        <span class="checkmark" id="radioAchats"></span>
                    </label>
                    <div class="checkbox">
                        <input type="checkbox" id="ouverte" name="checkOuverte" onclick="save()" class="checkAchats" >
                        <label for="ouverte" class="nomListe listeAchats" onclick="save()" id="labelOuverte">Enchères ouvertes</label>
                        <br>
                        <input type="checkbox" id="encours" name="checkEnCours" onclick="save()" class="checkAchats" >
                        <label for="encours" class="nomListe listeAchats"  id="labelEncours">Enchères en cours</label>
                        <br>
                        <input type="checkbox" id="remportees" name="checkRemportees" onclick="save()" class="checkAchats" >
                        <label for="remportees" class="nomListe listeAchats"  id="labelReportees">Enchères remportées</label>
                    </div>
                </div>

                <div id="mesventesGroupe" class="filtrage">
                    <label class="container" id="containerMesVentes">Mes ventes
                        <input type="radio" value="false" name="radio">
                        <span class="checkmark" id="radioMesVentes"></span>
                    </label>
                    <div class="checkbox">
                        <input type="checkbox" id="mesventesencours" name="checkMesVentesEnCours" onclick="save()"  class="checkMesVentes">
                        <label for="mesventesencours" class="nomListe venteEnCours"id="labelMesventesencours">Mes ventes en cours</label>
                        <br>
                        <input type="checkbox" id="nondebutees" name="checkNonDebutees" onclick="save()"  class="checkMesVentes" >
                        <label for="nondebutees" class="nomListe venteEnCours"id="labelNondebutees">Ventes non débutées</label>
                        <br>
                        <input type="checkbox" id="terminees" name="checkTerminees" onclick="save()" class="checkMesVentes">
                        <label for="terminees" class="nomListe venteEnCours"id="labelTerminees">Ventes terminées</label>
                    </div>
                </div>
            </c:if>

            <div id="cat">
                <label for="categories" id="legendeCat">Catégorie :</label>

                <select name="categories" id="categories" >
                    <option value="-1" <c:if test="${valeurRecherche != null}">selected</c:if>>-- Séléctionnez une catégorie --</option>
                    <option value="0" <c:if test="${idCategories == 0 }">selected</c:if>>Toutes</option>
                    <c:forEach items="${listeCategories}" var="listeCategories">
                        <option value="${listeCategories.noCategorie}"
                                <c:if test="${idCategories == listeCategories.noCategorie && valeurRecherche == null}">selected</c:if>>${listeCategories.libelle}</option>
                    </c:forEach>
                </select>
            </div>

            <div id="recherche">
                <span id="iconRecherche"><i class="fas fa-search"></i></span>
                <input id="articleRecherche" name="recherche" value="${requestScope.valeurRecherche}" placeholder="Nom de l'article">
                <input type="submit" value="Filtrer" name="rechercher" id="rechercher">
            </div>



        </div>
    </form>
</section>

<div id="contentArticles">
    <section id="articles">
        <c:forEach items="${listeArticles}" var="listeArticles">
            <a href="detail-vente?idArticle=${listeArticles.noArticle}">
                <div class="linkCard">
                    <div id="image">
                        <img src="${pageContext.request.contextPath.concat(File.separator).concat(Constants.UPLOAD_DIRECTORY).concat(File.separator).concat(listeArticles.imageName)}" alt="Image Article">
                    </div>
                    <div id="desc">
                        <%--TODO Modifier la comparaison avec les numéro de catégorie.      --%>
                        <span id="iconCat">
                            <c:if test="${listeArticles.categorie.noCategorie == 14}"><i class="fas fa-desktop iconCatArticle"></i></c:if>
                            <c:if test="${listeArticles.categorie.noCategorie == 15}"><i class="fas fa-couch iconCatArticle"></i></c:if>
                            <c:if test="${listeArticles.categorie.noCategorie == 16}"><i class="fas fa-tshirt iconCatArticle"></i></c:if>
                            <c:if test="${listeArticles.categorie.noCategorie == 17}"><i class="fas fa-gamepad iconCatArticle"></i></c:if>
                        </span>
                        <p>${listeArticles.nomArticle}</p>
                        <p>Mise à prix : ${listeArticles.prixInitial} points</p>
                        <c:if test="${listeArticles.prixVente > listeArticles.prixInitial}">
                            <p>Meilleure offre : ${listeArticles.prixVente} points</p>
                        </c:if>
                        <c:if test="${listeArticles.prixVente < listeArticles.prixInitial && listeArticles.comparaisonDateDebut >= 0}">
                            <p>Meilleure offre : aucune</p>
                        </c:if>
                        <c:if test="${listeArticles.comparaisonDateDebut < 0}">
                            <p>Début de l'enchère : ${listeArticles.dateDebutFormat}</p>
                        </c:if>
                        <c:if test="${listeArticles.comparaisonDateDebut >= 0}">
                            <p>Fin de l'enchère : ${listeArticles.dateFinFormat}</p>
                        </c:if>
                        <br>
                        <c:if test="${sessionScope.user == null}">
                        <p>Vendeur : ${listeArticles.utilisateur.pseudo}</p>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                        <p>Vendeur : <a id="lienProfil" href="profil?idProfil=${listeArticles.utilisateur.noUtilisateur}">${listeArticles.utilisateur.pseudo}</a>
                        </p>
                        </c:if>
                    </div>
                </div>
            </a>
        </c:forEach>
        <c:if test="${empty listeArticles}">
            <p id="articleNonTrouve">Pas d'article(s) trouvé(s)</p>
        </c:if>
    </section>
</div>
<c:if test="${sessionScope.user != null}">
    <script src="${pageContext.request.contextPath}/static/js/liste-encheres.js"></script>
</c:if>
<script type="text/javascript">

    // var chk1 = document.getElementById('ouverte');
    // var chk2 = document.getElementById('encours');
    // var chk3 = document.getElementById('remportees');
    // var chk4 = document.getElementById('mesventesencours');
    // var chk5 = document.getElementById('nondebutees');
    // var chk6 = document.getElementById('terminees');



    function save() {
        var chk1 = document.getElementById('ouverte');
        var chk2 = document.getElementById('encours');
        var chk3 = document.getElementById('remportees');
        var chk4 = document.getElementById('mesventesencours');
        var chk5 = document.getElementById('nondebutees');
        var chk6 = document.getElementById('terminees');



        localStorage.setItem('chk1', chk1.checked);
        localStorage.setItem('chk2', chk2.checked);
        localStorage.setItem('chk3', chk3.checked);
        localStorage.setItem('chk4', chk4.checked);
        localStorage.setItem('chk5', chk5.checked);
        localStorage.setItem('chk6', chk6.checked);
    }

    var checked1 = JSON.parse(localStorage.getItem("chk1"));
    document.getElementById("ouverte").checked = checked1;
    var checked2 = JSON.parse(localStorage.getItem("chk2"));
    document.getElementById("encours").checked = checked2;
    var checked3 = JSON.parse(localStorage.getItem("chk3"));
    document.getElementById("remportees").checked = checked3;
    var checked4 = JSON.parse(localStorage.getItem("chk4"));
    document.getElementById("mesventesencours").checked = checked4;
    var checked5 = JSON.parse(localStorage.getItem("chk5"));
    document.getElementById("nondebutees").checked = checked5;
    var checked6 = JSON.parse(localStorage.getItem("chk6"));
    document.getElementById("terminees").checked = checked6;


    var radios = document.getElementsByName("radio"); // list of radio buttons
    var val = localStorage.getItem('radio'); // local storage value
    for ( var i=0; i<radios.length; i++ ) {
        if( radios[i].value === val) {
            radios[i].checked = true; // marking the required radio as checked
        }
    }


        // function setStyles() {
        //     var currentColor = localStorage.getItem('bgcolor');
        //     var currentFont = localStorage.getItem('font');
        //     var currentImage = localStorage.getItem('image');
        //
        //     document.getElementById('bgcolor').value = currentColor;
        //     document.getElementById('font').value = currentFont;
        //     document.getElementById('image').value = currentImage;
        //
        //     htmlElem.style.backgroundColor = '#' + currentColor;
        //     pElem.style.fontFamily = currentFont;
        //     imgElem.setAttribute('src', currentImage);
        // }







</script>
<jsp:include page="footer.jsp"/>
</body>
</html>
