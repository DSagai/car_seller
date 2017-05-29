var passMatches;
const minCharactersCount = 3;
const passMatchesText = "Passwords match.";
const passNotMatchesText = "Passwords do not match.";

function init(){
    passMatches = false;
}

function comparePasswords() {
    var pass = document.getElementById("password").value;
    var confPass = document.getElementById("confirmPass").value;
    var passMatchElem = document.getElementById("matchPass");
    if (pass.length >= minCharactersCount && confPass.length >= minCharactersCount) {
        if (pass == confPass) {
            passMatches = true;
            passMatchElem.setAttribute("class", "valid");
            passMatchElem.textContent = passMatchesText
        } else {
            passMatches = false;
            passMatchElem.setAttribute("class", "error");
            passMatchElem.textContent = passNotMatchesText
        }
    } else {
        passMatches = false;
        passMatchElem.textContent = "";
    }

}

function onSubmit() {
    if (passMatches) {
        document.getElementById("user_form").submit();
    } else {
        alert(passNotMatchesText);
    }
}