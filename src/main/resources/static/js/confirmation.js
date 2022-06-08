function confirmation() {
    let x = confirm("Are you sure you want to delete this?");
    if (x) {
        alert("Action successfully executed");
        return true;
    } else {
        alert("Action canceled");
        return false;
    }
}


