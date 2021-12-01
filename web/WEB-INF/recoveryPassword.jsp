<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>

<jsp:include page="header.jsp">
  <jsp:param name="title" value="Connexion"/>
</jsp:include>

<body>

<h1 id="titreConnexion">Connexion</h1>

<div id="connexion">
  <form action="recoveryPassword" method="post" id="formRecoveryPassword" name="formRecoveryPassword">
    <label for="motDePasse" class="label-profil">Mot de passe :</label>
    <div class="col-10">
      <input class="input-profil" type="password" tabindex="9" id="motDePasse" name="motDePasse"
             placeholder="Votre mot de passe" value="">
    </div>
    <label for="confirmation" class="label-profil">Confirmation :</label>
    <div class="col-10">
      <input class="input-profil" type="password" id="confirmation" tabindex="10" name="confirmation"
             placeholder="Confirmation du mot de passe" value="">
    </div>
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
      errorPlacement: function (error, element) {
        element.attr("placeholder", error[0].outerText);
      },
      rules: {
        password: {
          required: true,
        }
      },
      messages: {
        password: {
          required: "Le champ email est obligatoire",
        }
      },
      submitHandler: function (form) {
        form.submit();
      }
    });
  });
</script>
</html>
