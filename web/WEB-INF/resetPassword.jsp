<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="header.jsp">
    <jsp:param name="title" value="Connexion"/>
</jsp:include>

<body>

<h1 id="titreConnexion">Oublié le mot de passe tu as !</h1>

<div id="connexion">
    <form action="resetPassword" method="post" id="formResetPassword" name="formResetPassword">
        <label for="email" class="labelConnexion">Email :</label>
        <input name="email" id="email" size="50" class="inputConnexion" />
        <br><br>
        <p id="messageErreur">
            ${message}
        </p>
        <button type="submit" id="btnConnexion">Envoyer</button>
    </form>
</div>


<jsp:include page="footer.jsp" />
</body>
<script type="text/javascript">

    $(document).ready(function() {
        $("form[name='formResetPassword']").validate({
            // errorPlacement: function (error, element) {
            //     element.attr("placeholder", error[0].outerText);
            // },
            rules: {
                email: {
                    required: true,
                    email: true,
                    remote: "profilValidation?status=recovery"
                }
            },
            messages: {
                email: {
                    required: "Le champ email est obligatoire",
                    email: "Veuillez saisir une adresse mail valide",
                    remote: "Nous pas connaitre toi"
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    });
</script>
</html>
