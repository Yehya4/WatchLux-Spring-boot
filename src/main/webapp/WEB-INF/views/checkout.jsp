<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Validation de commande - Mini E-Commerce</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <!-- NAVBAR SIMPLE (UNIQUEMENT CLIENT) -->
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand">MiniShop</a>
        <div class="navbar-links">
            <a href="${pageContext.request.contextPath}/produits" class="navbar-link">Boutique</a>
            <a href="${pageContext.request.contextPath}/panier" class="navbar-link active">Mon Panier</a>
            <a href="${pageContext.request.contextPath}/commandes" class="navbar-link">Mes Commandes</a>
            <span class="user-badge" style="background-color: var(--primary-color);">
                👤 ${sessionScope.currentUser.prenom} ${sessionScope.currentUser.nom}
            </span>
        </div>
    </nav>

    <!-- CONTENT -->
    <div class="container">
        <div class="page-header">
            <div>
                <h1 class="page-title">Finalisation de commande</h1>
                <p style="color: var(--text-secondary); margin-top: 0.25rem;">Veuillez indiquer l'adresse de livraison pour votre commande.</p>
            </div>
            <a href="${pageContext.request.contextPath}/panier" class="btn btn-secondary">Retour au panier</a>
        </div>

        <div style="display: grid; grid-template-columns: 1.5fr 1fr; gap: 2.5rem; align-items: flex-start;">
            
            <!-- CHECKOUT FORM -->
            <div class="card" style="padding: 2.5rem;">
                <h3 style="margin-bottom: 1.5rem; color: var(--secondary-color); font-size: 1.3rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.75rem;">Informations de Livraison</h3>
                
                <form action="${pageContext.request.contextPath}/commande/passer" method="POST">
                    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 1rem;">
                        <div class="form-group">
                            <label class="form-label">Prénom</label>
                            <input type="text" class="form-control" value="${sessionScope.currentUser.prenom}" disabled style="background-color: #f8fafc; cursor: not-allowed;">
                        </div>
                        <div class="form-group">
                            <label class="form-label">Nom</label>
                            <input type="text" class="form-control" value="${sessionScope.currentUser.nom}" disabled style="background-color: #f8fafc; cursor: not-allowed;">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="form-label">Adresse Email</label>
                        <input type="email" class="form-control" value="${sessionScope.currentUser.email}" disabled style="background-color: #f8fafc; cursor: not-allowed;">
                    </div>

                    <div class="form-group">
                        <label class="form-label" for="adresseDeLivraison">Adresse Complète de Livraison</label>
                        <textarea name="adresseDeLivraison" id="adresseDeLivraison" rows="4" class="form-control" placeholder="Indiquez l'adresse de livraison exacte (Rue, Ville, Code Postal...)" required style="resize: none; font-family: inherit;"></textarea>
                    </div>

                    <div style="margin-top: 2rem;">
                        <button type="submit" class="btn btn-primary" style="width: 100%; padding: 0.9rem; font-size: 1.1rem; border-radius: 8px;">Confirmer et passer au paiement</button>
                    </div>
                </form>
            </div>

            <!-- ORDER SUMMARY LIST -->
            <div class="card" style="background-color: #f8fafc; border-style: dashed; border-width: 2px;">
                <div class="card-body" style="padding: 2rem;">
                    <h3 style="margin-bottom: 1.5rem; color: var(--secondary-color); font-size: 1.2rem;">Résumé de commande</h3>
                    
                    <div style="max-height: 250px; overflow-y: auto; margin-bottom: 1.5rem; border-bottom: 1px solid var(--border-color); padding-bottom: 1rem;">
                        <c:forEach items="${panier.lignePaniers}" var="lp">
                            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1rem;">
                                <div>
                                    <div style="font-weight: 600; font-size: 0.95rem; color: var(--secondary-color);">${lp.produits.nom}</div>
                                    <div style="font-size: 0.8rem; color: var(--text-secondary);">Quantité: ${lp.quantite}</div>
                                </div>
                                <div style="font-weight: 600; font-size: 0.95rem; color: var(--secondary-color);">${lp.produits.prix * lp.quantite} DT</div>
                            </div>
                        </c:forEach>
                    </div>

                    <div style="display: flex; justify-content: space-between; font-size: 1.25rem; font-weight: 700; color: var(--secondary-color); margin-top: 1rem;">
                        <span>Montant Total</span>
                        <span style="color: var(--primary-color);">${total} DT</span>
                    </div>
                </div>
            </div>

        </div>
    </div>

</body>
</html>
