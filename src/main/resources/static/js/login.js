const password = document.getElementById("contrasena");
const toggle = document.getElementById("togglePassword");

toggle.addEventListener("click", () => {

    if (password.type === "password") {

        password.type = "text";
        toggle.textContent = "🐵";

    } else {

        password.type = "password";
        toggle.textContent = "🙈";

    }

});