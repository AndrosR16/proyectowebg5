// Contraseña
const password = document.getElementById("contrasena");
const togglePassword = document.getElementById("togglePassword");

if (password && togglePassword) {

    togglePassword.addEventListener("click", () => {

        if (password.type === "password") {
            password.type = "text";
            togglePassword.innerHTML = '<i class="fa-solid fa-eye"></i>';
        } else {
            password.type = "password";
           togglePassword.innerHTML = '<i class="fa-solid fa-eye-slash"></i>';
        }

    });

}

// Confirmar contraseña
const confirmarPassword = document.getElementById("confirmarContrasena");
const toggleConfirmarPassword = document.getElementById("toggleConfirmarPassword");

if (confirmarPassword && toggleConfirmarPassword) {

    toggleConfirmarPassword.addEventListener("click", () => {

        if (confirmarPassword.type === "password") {
            confirmarPassword.type = "text";
            toggleConfirmarPassword.innerHTML = '<i class="fa-solid fa-eye"></i>';
        } else {
            confirmarPassword.type = "password";
            toggleConfirmarPassword.innerHTML = '<i class="fa-solid fa-eye-slash"></i>';
        }

    });

}