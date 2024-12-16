const API_BASE = "http://localhost";

document.addEventListener("DOMContentLoaded", () => {
  // User Registration
  const registerForm = document.getElementById("register-form");
  const registerResponse = document.getElementById("register-response");

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

  // Create Help Request
  const requestForm = document.getElementById("request-form");
  const requestResponse = document.getElementById("request-response");

  requestForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const userId = document.getElementById("user-id").value;
    const details = document.getElementById("details").value;

    try {
      const response = await fetch(`${API_BASE}:8082/requests`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userId, details }),
      });
      const result = await response.json();
      requestResponse.textContent = `Demande créée : ID ${result.id}`;
    } catch (error) {
      requestResponse.textContent = "Erreur lors de la création de la demande.";
    }
  });

  // Fetch Help Requests
  const fetchRequestsButton = document.getElementById("fetch-requests");
  const requestList = document.getElementById("request-list");

  fetchRequestsButton.addEventListener("click", async () => {
    try {
      const response = await fetch(`${API_BASE}:8082/requests`);
      const requests = await response.json();

      requestList.innerHTML = ""; // Clear existing list
      requests.forEach((req) => {
        const li = document.createElement("li");
        li.textContent = `ID: ${req.id}, Détails: ${req.details}, Statut: ${req.status}`;
        requestList.appendChild(li);
      });
    } catch (error) {
      requestList.innerHTML = "<li>Erreur lors de la récupération des demandes.</li>";
    }
  });
});
