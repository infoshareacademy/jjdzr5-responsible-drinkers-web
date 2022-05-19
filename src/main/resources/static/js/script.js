function checkPassword() {
    let password = document.getElementById("password").value;
    let repeatPassword = document.getElementById("repeat_password").value;
    console.log(password, repeatPassword);

    if (password.length > 7) {
        if (password !== repeatPassword) {
            alert("Hasła muszą być jednakowe");
            return false;
        }
    }
    return true;
}