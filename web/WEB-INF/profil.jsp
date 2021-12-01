<%@ page import="fr.eni.bo.Utilisateur" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" scope="page" class="fr.eni.bo.Utilisateur" />

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Profil"/>
</jsp:include>

<body>
<h1>Profil</h1>

<div class="wrapper">
    <div class="wrapper-op">
        <form method="post" action="${pageContext.request.contextPath}/profil" name="profileFormulaire" id="profileFormulaire">
            <input type="hidden" name="status" id="status" value="update">
            <input type="hidden" name="idu" id="idu" value="${requestScope.user.noUtilisateur}">
            <input type="hidden" name="ida" id="ida" value="${requestScope.user.adresse.id}">
            <div class="col-container">
                <div class="col-1">

                    <div class="col-10">
                        <label for="pseudo" class="label-profil">Pseudo :</label>
                        <c:if test="${utilisateur == null}">
                            <input class="input-profil" type="text" id="pseudo" tabindex="1" name="pseudo" placeholder="Votre pseudo" value="${userSession.pseudo}">
                        </c:if>
                        <c:if test="${utilisateur != null}">
                            <input class="input-profil-other" type="text" id="pseudo" tabindex="1" name="pseudoOther" placeholder="Votre pseudo" value="${utilisateur.pseudo}" disabled>
                        </c:if>
                    </div>

                    <div class="col-10">
                        <label for="prenom" class="label-profil">Prénom :</label>
                        <c:if test="${utilisateur == null}">
                            <input class="input-profil" type="text" id="prenom" tabindex="3" name="prenom" placeholder="Votre prénom" value="${userSession.prenom}">
                        </c:if>
                        <c:if test="${utilisateur != null}">
                            <input class="input-profil-other-flou" type="text" id="prenom" tabindex="3" name="prenom" placeholder="Votre prénom" value="aspernatur" disabled>
                        </c:if>
                    </div>

                    <div class="col-10">
                        <label for="telephone" class="label-profil">Téléphone :</label>
                        <c:if test="${utilisateur == null}">
                            <input class="input-profil" type="text" tabindex="5" id="telephone" name="telephone" placeholder="Votre numéro de téléphone" value="${userSession.telephone}">
                        </c:if>
                        <c:if test="${utilisateur != null}">
                            <input class="input-profil-other-flou" type="text" tabindex="5" id="telephone" name="telephone" placeholder="Votre numéro de téléphone" value="0102030405" disabled>
                        </c:if>
                    </div>
                    <div class="col-10">
                        <label for="codePostal" class="label-profil">Code postal :</label>
                        <c:if test="${utilisateur == null}">
                            <input class="input-profil" type="text" tabindex="7" id="codePostal" name="codePostal" placeholder="Votre code postal" value="${userSession.adresse.codePostal}">
                        </c:if>
                        <c:if test="${utilisateur != null}">
                            <input class="input-profil-other-flou" type="text" tabindex="7" id="codePostal" name="codePostal" placeholder="Votre code postal" value="00000" disabled>
                        </c:if>
                    </div>
                    <c:if test="${utilisateur == null}">
                    <div class="col-10">
                        <label for="motDePasse" class="label-profil">Mot de passe :</label>
                        <input class="input-profil" type="password" tabindex="9" id="motDePasse" name="motDePasse" placeholder="Votre mot de passe">
                    </div>
                    </c:if>
                    <c:if test="${utilisateur == null}">
                        <div class="col-10">
                            <label for="credit" class="label-profil">Crédit :</label>
                            <c:if test="${userSession.credit <= 1}">
                                <input class="input-profil-other" type="text" tabindex="10" id="credit" name="motDePasse" placeholder="Votre mot de passe" value="${userSession.credit} point" disabled>
                            </c:if>
                            <c:if test="${userSession.credit > 1}">
                                <input class="input-profil-other" type="text" tabindex="10" id="credit" name="motDePasse" placeholder="Votre mot de passe" value="${userSession.credit} points" disabled>
                            </c:if>

                        </div>
                    </c:if>
                </div>


                <div class="col-2">

                    <div class="col-10">
                        <label for="nom" class="label-profil">Nom :</label>
                        <c:if test="${utilisateur == null}">
                            <input class="input-profil" type="text" id="nom" tabindex="2" name="nom" placeholder="Votre nom" value="${userSession.nom}">
                        </c:if>
                        <c:if test="${utilisateur != null}">
                            <input class="input-profil-other-flou" type="text" id="nom" tabindex="2" name="nom" placeholder="Votre nom" value="reiciendis" disabled>
                        </c:if>
                    </div>


                    <div class="col-10">
                        <label for="email" class="label-profil">E-mail :</label>
                        <c:if test="${utilisateur == null}">
                            <input class="input-profil" type="text" id="email" tabindex="4" name="email" placeholder="Votre e-mail" value="${userSession.email}">
                        </c:if>
                        <c:if test="${utilisateur != null}">
                            <input class="input-profil-other-flou" type="text" id="email" tabindex="4" name="email" placeholder="Votre e-mail" value="necessitatibus@Lorem-ipsum.com" disabled>
                        </c:if>
                    </div>


                    <div class="col-10">
                        <label for="rue" class="label-profil">Rue :</label>
                        <c:if test="${utilisateur == null}">
                            <input class="input-profil" type="text" id="rue" tabindex="6" name="rue" placeholder="Votre rue" value="${userSession.adresse.rue}">
                        </c:if>
                        <c:if test="${utilisateur != null}">
                            <input class="input-profil-other-flou" type="text" id="rue" tabindex="6" name="rue" placeholder="Votre rue" value="00 consectetur adipisicing elit" disabled>
                        </c:if>
                    </div>

                    <div class="col-10">
                        <label for="ville" class="label-profil">Ville :</label>
                        <c:if test="${utilisateur == null}">
                            <input class="input-profil" type="text" id="ville" tabindex="8" name="ville" placeholder="Votre ville" value="${userSession.adresse.ville}">
                        </c:if>
                        <c:if test="${utilisateur != null}">
                            <input class="input-profil-other-flou" type="text" id="ville" tabindex="8" name="ville" placeholder="Votre ville" value="Voluptas" disabled>
                        </c:if>

                    </div>


                    <div class="col-10">
                        <c:if test="${utilisateur == null}">
                        <label for="confirmation" class="label-profil">Confirmation :</label>
                        <input class="input-profil" type="password" id="confirmation" tabindex="10" name="confirmation" placeholder="Confirmation du mot de passe" value="${userSession.motDePasse}">
                        </c:if>
    </div>
                </div>
            </div>
            <div id="profil">
                <c:if test="${utilisateur == null}">
                <span id="iconSaveProfil"><i class="fas fa-save iconProfilSave"></i></span>
                <input type="submit" name="btnSubmit" value="${requestScope.btnValidateProfile}" class="btnProfil">

                <span id="iconCancelProfil"><i class="fas fa-trash-alt iconProfilCancel"></i></span>
                <input type="button" onclick="window.location.href='liste-encheres'" value="Annuler" class="btnProfil">

                <span id="iconDeleteProfil"><i class="fas fa-user-times iconProfilDelete"></i></span>
                <input type="button" name="btnDelete" onclick="window.location.href='delete?delete=${requestScope.user.noUtilisateur}'" id="delete" value="Supprimer" class="btnProfil">
                </c:if>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">

    $(function () {

        jQuery.validator.addMethod(
            "regex",
            function(value, element, regexp) {
                if (regexp.constructor != RegExp)
                    regexp = new RegExp(regexp);
                else if (regexp.global)
                    regexp.lastIndex = 0;
                return this.optional(element) || regexp.test(value);
            },"erreur expression reguliere"
        );

        $("form[name='profileFormulaire']").validate({
            errorPlacement: function (error, element) {
                element.attr("placeholder", error[0].outerText);
            },
            rules: {
                prenom: "required",
                pseudo: {
                    required: true,
                    remote: "profilValidation?status=update"
                },
                nom: "required",
                codePostal: {
                    required: true,
                    digits: true,
                    minlength: 5
                },
                rue: "required",
                ville: "required",
                email: {
                    required: true,
                    email: true,
                    minlength: 6,
                    remote: "profilValidation?status=update "
                },
                telephone: {
                    required: true,
                    regex: /^(\+33|0033|0)([-. ]?)[1-78]([-. ]?[0-9]{2}){4}$/
                },
                motDePasse: {
                    required: true,
                    minlength: 10,

                },
                confirmation: {
                    required: true,
                    equalTo: "#motDePasse"
                }
            },
            // Définition des messages d'erreurs
            messages: {
                pseudo: "c'est quoi ton ptit surnom",
                prenom: "Comment tu t'appel ??",
                nom: "C'est quoi ton nom ?",
                telephone: "t'as un 06 ?",
                rue: "tu n'as pas mis ta rue",
                codePostal: "un code postal plz",
                ville: "avec la ville c'est mieux",
                motDePasse: {
                    required: "Le mot de passe c'est pas pour les chiens",
                    minlength: "C'est bien trop court pour un mot de passe"
                },
                email: "Un email valide c'est possible ?"
            },
            //on s'assure que le formulaire est submité vers la destination
            //définis dans l'attribut action du formulaire ici tulututuServlet
            //uniquement si tout le formulaire est validé
            submitHandler: function (form) {
                form.submit();
            }
        });
    });
// TODO Appel ajax pour les emails en double
</script>

<jsp:include page="footer.jsp"/>
</body>
</html>
