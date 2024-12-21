const API_BASE = "http://localhost";

// Append the user-registration dropdown for each role from the database
function roleDefine() {
    const select = document.getElementById("role");
    fetchData(`${API_BASE}:${ROLE_PORT}/get_roles`).then(data => {
        data.forEach(element => {
            const option = createOption(element.id, element.name);
            select.appendChild(option);
        });
    });
}

document.addEventListener("DOMContentLoaded", () => {
    roleDefine();

    const loginForm = document.getElementById("login-form");
    const loginResponse = document.getElementById("login-response");
    const registerForm = document.getElementById("register-form");
    const registerResponse = document.getElementById("register-response");
    const userRegistrationSection = document.getElementById("user-registration");

    // Check if user is logged in
    if (localStorage.getItem("userId")) {
        userRegistrationSection.style.display = "none";
    }

    // User Login
    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const email = document.getElementById("login-email").value;
        const password = document.getElementById("login-password").value;

        try {
            const response = await fetch(`${API_BASE}:8083/users/authenticate`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password }),
            });
            const result = await response.json();
            if (result.success) {
                localStorage.setItem("userId", result.userId);
                loginResponse.textContent = `Connexion réussie : ID ${result.userId}`;
                userRegistrationSection.style.display = "none";
            } else {
                loginResponse.textContent = `Échec de la connexion : ${result.message}`;
            }
        } catch (error) {
            loginResponse.textContent = "Erreur lors de la connexion.";
        }
    });

    // User Registration
    registerForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const role = document.getElementById("role").value;

        try {
            const response = await fetch(`${API_BASE}:8083/users`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ name, email, password, role }),
            });
            const result = await response.json();
            registerResponse.textContent = `Utilisateur créé : ID ${result.id}`;
        } catch (error) {
            registerResponse.textContent = "Erreur lors de la création de l'utilisateur.";
        }
    });
});