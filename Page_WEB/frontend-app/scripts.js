// MODIFIEZ CES URLS pour qu'elles correspondent à vos services
const API_URL_USERS    = 'http://localhost:8080/api/clients/1';
const API_URL_PRODUCTS = 'http://localhost:8082/api/produits';
const API_URL_ORDERS   = 'http://localhost:8081/api/commandes/1';

document.addEventListener('DOMContentLoaded', chargerToutesLesDonnees);

async function chargerToutesLesDonnees() {
  const erreurDiv = document.getElementById('messages-erreur');
  erreurDiv.textContent = ''; // Vider les messages d'erreur

  try {
    // 1. Lancer les 3 requêtes en parallèle (très efficace !)
    const [reponseUsers, reponseProduits, reponseCommandes] = await Promise.all([
      fetch(API_URL_USERS),
      fetch(API_URL_PRODUCTS),
      fetch(API_URL_ORDERS)
    ]);

    // 2. Vérification des réponses HTTP
    if (!reponseUsers.ok) { throw new Error(`Erreur Utilisateur (${reponseUsers.status})`); }
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

  } catch (erreur) {
    console.error('Erreur lors du chargement des données:', erreur);
    erreurDiv.textContent = `Erreur: Impossible de charger les données. Vérifiez la console et le CORS. (${erreur.message})`;
  }
}

function mettreAJourLaPage(user, produits, commandes) {
  // Mise à jour du nom d'utilisateur
  document.getElementById('nom-utilisateur').textContent = user.nom || 'Inconnu';

  // Mise à jour de la liste des produits
  const listeProduits = document.getElementById('liste-produits');
  listeProduits.innerHTML = '';
  produits.forEach(produit => {
    const li = document.createElement('li');
    li.textContent = `${produit.nom} (ID: ${produit.id})`;
    listeProduits.appendChild(li);
  });
  
  console.log('Toutes les données sont chargées et affichées !');
}