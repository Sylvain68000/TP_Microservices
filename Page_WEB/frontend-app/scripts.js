// Définition de l'adresse de l'hôte Windows
const HOST_IP = '172.28.192.1'; // <-- VOTRE ADRESSE IP DE L'HÔTE WINDOWS

document.addEventListener('DOMContentLoaded', chargerToutesLesDonnees);

async function chargerToutesLesDonnees() {
    const erreurDiv = document.getElementById('messages-erreur');
    erreurDiv.textContent = ''; // Vider les messages d'erreur

    // --- 1. Récupérer l'ID du client (nécessaire pour les requêtes) ---
    // Simule la récupération de l'ID après login.
    const CLIENT_ID = localStorage.getItem('clientID') || 1; // Utilise 1 par défaut si non stocké

    // 2. Définition des URLS avec l'IP de l'hôte et l'ID du client
    const URL_USERS = `http://${HOST_IP}:8080/api/clients/${CLIENT_ID}`;
    const URL_PRODUCTS = `http://${HOST_IP}:8082/api/produits`;
    const URL_ORDERS = `http://${HOST_IP}:8081/api/commandes/${CLIENT_ID}`;
    
    try {
        // Lancer les 3 requêtes en parallèle
        const [reponseUsers, reponseProduits, reponseCommandes] = await Promise.all([
            fetch(URL_USERS),
            fetch(URL_PRODUCTS),
            fetch(URL_ORDERS)
        ]);

        // ... (Le reste de votre logique de vérification et de conversion en JSON) ...
        
        // 2. Vérification des réponses HTTP
        if (!reponseUsers.ok) { throw new Error(`Erreur Client (${reponseUsers.status})`); }
        if (!reponseProduits.ok) { throw new Error(`Erreur Produits (${reponseProduits.status})`); }
        if (!reponseCommandes.ok) { throw new Error(`Erreur Commandes (${reponseCommandes.status})`); }

        // 3. Conversion des données en JSON
        const [dataUsers, dataProduits, dataCommandes] = await Promise.all([
            reponseUsers.json(),
            reponseProduits.json(),
            reponseCommandes.json()
        ]);

        // 4. Mettre à jour la page avec les données
        mettreAJourLaPage(dataUsers, dataProduits, dataCommandes);

    } catch (error) {
        console.error('Erreur lors du chargement des données:', error);
        
        // Le message d'erreur est propre maintenant
        document.getElementById('messages-erreur').textContent = 
            `Erreur: ${error.message}. Vérifiez si l'IP de l'hôte est à jour et si les microservices sont démarrés.`;
    }
}
// La fonction mettreAJourLaPage() reste inchangée
// ...