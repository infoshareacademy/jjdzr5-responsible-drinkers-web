function checkPassword(){
    let password = document.getElementById("password").value;
    let confirmPassword = document.getElementById("confirm_password").value;
    console.log(password,confirmPassword);

    if(password.length !== 0){
        if (password !== confirmPassword) {
            alert("Hasła muszą być jednakowe");
        }
    } else {
        alert("Pole hasło nie może być puste");
    }
}