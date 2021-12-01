<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/liste-encheres.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/connexion.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/profil.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/detail-vente.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/ajout-article.css" />
    <link href="${pageContext.request.contextPath}/static/js/zoombox/zoombox.css" rel="stylesheet" type="text/css" media="screen" />
    <script src="https://kit.fontawesome.com/9b87c4faa8.js" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/moment.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/zoombox/zoombox.js"></script>
    <script>
        jQuery(function($){
            $('a.zoombox').zoombox();
        });
    </script>
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/static/img/icon.png"/>
    <link href="https://fonts.googleapis.com/css2?family=Carter+One&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@300;400;500;600;700&family=Quicksand:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <title>${param.title}</title>
</head>
<div id="contenttop">
    <div id="top">
        <div id="logo">
            <a href="liste-encheres"><img src="${pageContext.request.contextPath}/static/img/logo-enchere.png" alt="logo"></a>
        </div>

        <c:if test="${sessionScope.user == null}" >
            <nav id="menuDeconnecte" class="menu">

                <ul>
                    <li><a href="createUser">S'inscrire</a></li>
                    <li><a href="connexion">Se connecter</a></li>
                </ul>
            </nav>


        </c:if>
        <c:if test="${sessionScope.user != null}" >
            <nav id="menuConnecte" class="menu">
                <ul>
                    <li><a href="liste-encheres">Enchères</a></li>
                    <li><a href="ajout-article">Vendre un article</a></li>
                    <li><a href="profil">Mon profil</a></li>
                    <li><a href="deconnexion">Déconnexion</a></li>
                </ul>
            </nav>
        </c:if>
    </div>
    <c:if test="${sessionScope.user == null}" >
        <div id="promo">
            <a href="createUser"><i class="fas fa-bullhorn"></i> Pour toute inscription en juin et juillet 2021, 640 points offerts</a>
        </div>
    </c:if>
</div>


