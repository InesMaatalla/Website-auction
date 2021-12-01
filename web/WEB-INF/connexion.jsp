<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Connexion"/>
</jsp:include>

<body>

<h1 id="titreConnexion">Connexion</h1>

<div id="connexion">
    <form action="connexion" method="post" id="formConnexion" name="formConnexion">
        <label for="email" class="labelConnexion">Email :</label>
        <input name="email" id="email" size="50" class="inputConnexion" />
        <br><br>
        <label for="password" class="labelConnexion">Mot de Passe :</label>
        <input type="password" id="password" name="password" size="30" class="inputConnexion" />
        <br>
        <a href="resetPassword" id="mdpOublie">Mot de passe oublié ?</a>
        <p id="messageErreur">
            ${message}
        </p>
        <button type="submit" id="btnConnexion">Se connecter</button>
        <div class="checkbox">
            <input  type="checkbox" id="seSouvenirCheck" name="scales" >
            <label for="seSouvenir" id="seSouvenir">Se souvenir de moi</label>
        </div>
    </form>
</div>


<jsp:include page="footer.jsp" />
</body>
<script type="text/javascript">

    $(document).ready(function() {
        $("form[name='formConnexion']").validate({
            errorPlacement: function (error, element) {
                element.attr("placeholder", error[0].outerText);
            },
            rules: {
                email: {
                    required: true,
                    email: true
                },
                password: "required",
            },
            messages: {
                email: {
                    required: "Le champ email est obligatoire",
                    email: "Veuillez saisir une adresse mail valide"
                },
                password: "Le mot de passe est obligatoire"
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    });
</script>
</html>
