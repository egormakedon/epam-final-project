function checkPassword() {
    if (document.getElementById('password1').value != document.getElementById('password2').value) {
        alert('Passwords do not match');
        return false;
    } else return true;
}