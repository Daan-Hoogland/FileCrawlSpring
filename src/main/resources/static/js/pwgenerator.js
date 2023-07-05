// Generate random strong 12 character password
function randomPassword(length) {
    var chars = "abcdefghijklmnopqrstuvwxyz!@#$%&*ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    var pass = "";
    for (var x = 0; x < length; x++) {
        var i = Math.floor(Math.random() * chars.length);
        pass += chars.charAt(i);
    }
    return pass;
}

if (!(document.getElementById("pwd").placeholder === "Wijzig wachtwoord...")) document.getElementById("pwd").value = randomPassword(12);

// Show/Hide text in password field
function showpwd() {
    var p = document.getElementById('pwd');
    p.setAttribute('type', 'text');
}

function hidepwd() {
    var p = document.getElementById('pwd');
    p.setAttribute('type', 'password');
}

var pwShown = 0;

document.getElementById("eye").addEventListener("click", function () {
    if (pwShown == 0) {
        pwShown = 1;
        showpwd();
    } else {
        pwShown = 0;
        hidepwd();
    }
}, false);