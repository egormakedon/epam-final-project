'use strict';

function checkPassword() {
    let password1Value = document.getElementById('password1').value;
    let password2Value = document.getElementById('password2').value;

    if (password1Value !== password2Value) {
        alert('Passwords do not match');
        return false;
    }
    return true;
}
