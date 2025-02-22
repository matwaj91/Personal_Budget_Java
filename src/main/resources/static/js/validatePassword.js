$(document).ready(function () {
    $('#formProfile').validate({
        rules: {
            password: {
                minlength: 6,
                validPassword: true
            }
        },
    });
});