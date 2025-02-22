function validateForm() {
    var input1 = document.forms["formProfile"]["name"].value;
    var input2 = document.forms["formProfile"]["password"].value;
    var errorMessage = document.getElementById("error-message");

    if (input1 == "" && input2 == "") {
        errorMessage.style.display = "block";
        return false;
    }
    errorMessage.innerHTML = "";
    errorMessage.style.display = "none";
    return true;
}