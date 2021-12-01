// Déclaration des variables

const checkMesVentes = document.getElementsByClassName("checkMesVentes");
const checkAchats = document.getElementsByClassName("checkAchats");
const venteEnCours = document.getElementsByClassName("venteEnCours");
const listeAchats = document.getElementsByClassName("listeAchats");

// Mettre les checkbox de "Mes ventes" en disable

for (let i = 0; i < checkMesVentes.length; i++) {
    checkMesVentes[i].disabled = true;
}

// Modifier la couleur et l'opacité des label Checkbox de "Mes ventes"

for (let i = 0; i < venteEnCours.length; i++) {
    venteEnCours[i].style.color = '#f1f1f1';
    venteEnCours[i].style.opacity = '70%';
}

document.getElementById("containerMesVentes").addEventListener('click', function () {

    for (let i = 0; i < venteEnCours.length; i++) {
        venteEnCours[i].style.color = '#FFFFFF';
        venteEnCours[i].style.opacity = '100%';
    }

    for (let i = 0; i < listeAchats.length; i++) {
        listeAchats[i].style.color = '#f1f1f1';
        listeAchats[i].style.opacity = '70%';
    }

    for (let i = 0; i < checkMesVentes.length; i++) {
        checkMesVentes[i].disabled = false;
    }

    for (let i = 0; i < checkAchats.length; i++) {
        checkAchats[i].disabled = true;
    }

    document.getElementById("ouverte").checked = false;
    document.getElementById("encours").checked = false;
    document.getElementById("remportees").checked = false;

});

document.getElementById("containerAchats").addEventListener('click', function () {

    for (let i = 0; i < venteEnCours.length; i++) {
        venteEnCours[i].style.color = '#f1f1f1';
        venteEnCours[i].style.opacity = '70%';
    }

    for (let i = 0; i < listeAchats.length; i++) {
        listeAchats[i].style.color = '#FFFFFF';
        listeAchats[i].style.opacity = '100%';
    }

    for (let i = 0; i < checkMesVentes.length; i++) {
        checkMesVentes[i].disabled = true;
    }

    for (let i = 0; i < checkAchats.length; i++) {
        checkAchats[i].disabled = false;
    }

    document.getElementById("mesventesencours").checked = false;
    document.getElementById("nondebutees").checked = false;
    document.getElementById("terminees").checked = false;

});

for (let i = 0; i < checkAchats.length; i++) {

    checkAchats[i].addEventListener('click', function() {
        if (checkAchats[i].value === 'true') {
            checkAchats[i].value = false;
        } else {
            checkAchats[i].value = true;
        }
    });

}for (let i = 0; i < checkMesVentes.length; i++) {

    checkMesVentes[i].addEventListener('click', function() {
        if (checkMesVentes[i].value === 'true') {
            checkMesVentes[i].value = false;
        } else {
            checkMesVentes[i].value = true;
        }
    });

}