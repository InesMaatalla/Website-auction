<%@ page import="fr.eni.bo.Article" %>
<%@ page import="jdk.jfr.Description" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%@ page import="fr.eni.Utils.Constants" %>
<%@ page import="java.io.File" %>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Ajouter un article"/>
</jsp:include>

<body>
<h1 id="titreNouvelleVente">Nouvelle vente</h1>

<div id="ajoutVenteContainer">
    <div id="venteBody">
        <div id="imageArticle">
            <img src="${pageContext.request.contextPath.concat(File.separator).concat(Constants.UPLOAD_DIRECTORY).concat(File.separator).concat("image-not-found.jpg")}" id="imgArt" alt="Image article">
        </div>
        <div id="formulaireAjoutVente">
            <form action="ajout-article" method="post" id="formAjoutArticle" name="formulaireAjoutVente" enctype="multipart/form-data">
                <div class="infosArticle">
                    <label for="nomArticle" class="labelArticle" >Article :</label>
                    <input name="nomArticle" id="nomArticle" class="inputAjoutArticle" />
                </div>

                <div class="infosArticle">
                    <label for="textareaDescription" id="labelDescription" class="labelArticle">Description :</label>
                    <textarea name="description" cols="30" rows="10" class="textareaDescription" id="textareaDescription"></textarea>
                </div>

                <div class="infosArticle">
                    <label for="categoriesAjoutArticle" class="labelArticle">Catégorie :</label>
                    <select name="categories" id="categoriesAjoutArticle" class="categoriesAjoutArticle">
                        <option value="" >Séléctionner une catégorie</option>
                        <c:forEach items="${listeCategories}" var="listeCategories">
                            <option name="idCategorie" value="${listeCategories.noCategorie}">${listeCategories.libelle}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="infosArticle">
                    <label for="imgArticle" class="labelArticle">Photo de l'article :</label>
                    <input type="file" id="imgArticle" class="inputPhotoArticle" name="imgArticle"  onchange="readURL(this);" accept="image/png, image/jpeg">

                </div>
                <div class="infosArticle">
                    <label for="prixArticle" class="labelArticle">Mise à prix :</label>
                    <input type="number" id="prixArticle" class="inputAjoutArticle" name="prixArticle" min="0">
                </div>

                <div class="infosArticle">
                    <label for="dateDebutArticle" class="labelArticle">Début de l'enchère :</label>
                    <input type="date" id="dateDebutArticle" class="inputAjoutArticle" name="dateDebutArticle">
                </div>

                <div class="infosArticle">
                    <label for="dateFinArticle" class="labelArticle">Fin de l'enchère :</label>
                    <input type="date" id="dateFinArticle" class="inputAjoutArticle" name="dateFinArticle">
                </div>

                <div id="retraitArticle">
                    <p id="adresseDeRetrait">Adresse de retrait</p>

                    <div class="infosArticle">
                        <label for="rueRetrait" class="labelArticle" id="labelRue">Rue :</label>
                        <input name="rueRetrait" id="rueRetrait" class="inputAjoutArticle" value="${rue}"/>
                    </div>
                    <div class="infosArticle">
                        <label for="codePostalRetrait" class="labelArticle" id="labelCodePostal">Code postal :</label>
                        <input name="codePostalRetrait" id="codePostalRetrait" class="inputAjoutArticle" value="${codePostal}" />
                    </div>
                    <div class="infosArticle">
                        <label for="villeRetrait" class="labelArticle" id="labelVille">Ville :</label>
                        <input name="villeRetrait" id="villeRetrait" class="inputAjoutArticle" value="${ville}"/>
                    </div>

                </div>

                <div id="boutonArticle">
                    <span id="iconSave"><i class="fas fa-save"></i></span>
                    <input type="submit" value="Enregistrer" name="enregistrer" id="enregistrer" form="formAjoutArticle">

                    <span id="iconCancel"><i class="fas fa-trash-alt"></i></span>
                    <input type="button" value="Annuler" name="annuler" id="annuler" onclick="window.location.href='liste-encheres'">
                </div>
            </form>
        </div>

    </div>
</div>
<script type="text/javascript">




    function readURL(input) {
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                $('#imgArt')
                    .attr('src', e.target.result);
                    // .width(150)
                    // .height(200);
            };
            reader.readAsDataURL(input.files[0]);
        }
    }

    $(function () {
        // TODO la date de fin d'une enchere devrait etre la date de début + 1 jour
        document.getElementById("dateFinArticle").min = moment().format('YYYY-MM-DD');
        //document.getElementById("dateFinArticle").max = moment().format('YYYY-MM-DD');
        document.getElementById("dateDebutArticle").min = moment().format('YYYY-MM-DD');
        //document.getElementById("dateDebutArticle").max = moment().format('YYYY-MM-DD');

        document.getElementById("dateDebutArticle").addEventListener('change', function () {
            //$("#dateFinArticle").prop('max', )
            document.getElementById("dateFinArticle").min = document.getElementById("dateDebutArticle").value;
        });
        document.getElementById("dateFinArticle").addEventListener('change', function () {
            //$("#dateFinArticle").prop('max', )
            document.getElementById("dateDebutArticle").max = document.getElementById("dateFinArticle").value;
        });


        $("form[name='formulaireAjoutVente']").validate({
            errorPlacement: function (error, element) {
                element.attr("placeholder", error[0].outerText);
            },
            rules: {
                nomArticle: "required",
                description: "required",
                categories: "required",
                prixArticle: {
                    required: true,
                    digits: true,
                    minlength: 1
                },
                dateDebutArticle: "required",
                dateFinArticle: "required",
                rueRetrait: "required",
                codePostalRetrait: "required",
                villeRetrait: "required",

            },
            // Définition des messages d'erreurs
            messages: {
                nomArticle: "Tu vends rien ?",
                description: "Description obligatoire",
                prixArticle: "C'est gratuit ?",
                dateDebutArticle: "jj/mm/aaaa",
                dateFinArticle: "jj/mm/aaaa",
                rueRetrait: "Champ obligatoire",
                codePostalRetrait: "Champ obligatoire",
                villeRetrait: "Champ obligatoire",
            },
            //on s'assure que le formulaire est submité vers la destination
            //définis dans l'attribut action du formulaire ici tulututuServlet
            //uniquement si tout le formulaire est validé
            submitHandler: function (form) {
                form.submit();
            }
        });
    });

</script>
<jsp:include page="footer.jsp" />
</body>
</html>
