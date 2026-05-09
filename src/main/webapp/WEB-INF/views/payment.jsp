<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paiement Sécurisé - Mini E-Commerce</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body style="background: radial-gradient(circle at top, #f8fafc, #f1f5f9);">

    <!-- NAVBAR SIMPLE (UNIQUEMENT CLIENT) -->
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand">MiniShop</a>
        <div class="navbar-links">
            <a href="${pageContext.request.contextPath}/produits" class="navbar-link">Boutique</a>
            <a href="${pageContext.request.contextPath}/panier" class="navbar-link">Mon Panier</a>
            <a href="${pageContext.request.contextPath}/commandes" class="navbar-link active">Mes Commandes</a>
            <span class="user-badge" style="background-color: var(--primary-color);">
                👤 ${sessionScope.currentUser.prenom} ${sessionScope.currentUser.nom}
            </span>
        </div>
    </nav>

    <!-- CONTENT -->
    <div class="container" style="max-width: 650px; margin-top: 4rem;">
        
        <div class="card" style="padding: 3rem; border-top: 6px solid var(--primary-color); box-shadow: 0 20px 25px -5px rgb(0 0 0 / 0.1), 0 8px 10px -6px rgb(0 0 0 / 0.1);">
            <div style="text-align: center; margin-bottom: 2rem;">
                <div style="font-size: 3rem; margin-bottom: 0.5rem;">🔒</div>
                <h1 style="font-size: 1.75rem; font-weight: 700; color: var(--secondary-color);">Simulation de Paiement Sécurisé</h1>
                <p style="color: var(--text-secondary); margin-top: 0.25rem;">Réglez votre commande en toute sécurité avec nos différents modes de paiement.</p>
            </div>

            <c:if test="${param.error != null}">
                <div class="alert alert-danger" style="margin-bottom: 2rem;">
                    Erreur de traitement du paiement : ${param.error}
                </div>
            </c:if>

            <div class="summary-card" style="background: linear-gradient(135deg, #4f46e5, #4338ca); border: none; margin-bottom: 2.5rem;">
                <div class="summary-details">
                    <h3 style="color: white; font-weight: 600; font-size: 1.2rem;">Commande #CMD-${commande.id}</h3>
                    <p style="color: #c7d2fe;">Destinataire : ${commande.utilisateur.prenom} ${commande.utilisateur.nom}</p>
                </div>
                <div class="summary-amount" style="color: white; font-size: 2rem;">${commande.montant} DT</div>
            </div>

            <form action="${pageContext.request.contextPath}/paiement/payer" method="POST">
                <input type="hidden" name="commandeId" value="${commande.id}">
                <input type="hidden" name="montant" value="${commande.montant}">

                <div class="form-group">
                    <label class="form-label" for="methode">Sélectionnez un moyen de paiement</label>
                    <select name="methode" id="methode" class="form-control" required style="padding: 0.8rem; font-size: 1rem;">
                        <c:forEach items="${paymentMethods}" var="method">
                            <option value="${method}" ${method == 'CARTE_BANCAIRE' ? 'selected' : ''}>
                                <c:choose>
                                    <c:when test="${method == 'CARTE_BANCAIRE'}">💳 Carte Bancaire</c:when>
                                    <c:when test="${method == 'PAYPAL'}">📱 PayPal</c:when>
                                    <c:when test="${method == 'VIREMENT'}">🏦 Virement Bancaire</c:when>
                                    <c:otherwise>💵 Paiement Cash à la livraison</c:otherwise>
                                </c:choose>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div style="margin-top: 1rem; border: 1px solid var(--border-color); background-color: #fafafa; border-radius: 8px; padding: 1.25rem; font-size: 0.85rem; color: var(--text-secondary); line-height: 1.5; margin-bottom: 2.5rem;">
                    💡 En cliquant sur "Confirmer le paiement", vous simulez un retour positif de la passerelle bancaire. Le statut de votre commande passera automatiquement à <strong>EN_ATTENTE</strong> ou <strong>CONFIRMEE</strong> et le paiement sera marqué comme <strong>APPROUVE</strong>.
                </div>

                <div style="display: flex; gap: 1rem;">
                    <a href="${pageContext.request.contextPath}/commandes" class="btn btn-secondary" style="flex: 1; padding: 0.8rem; font-size: 1rem;">Payer plus tard</a>
                    <button type="submit" class="btn btn-primary" style="flex: 1.5; padding: 0.8rem; font-size: 1rem; background-color: var(--success-color);">Confirmer le paiement</button>
                </div>
            </form>
        </div>

    </div>

</body>
</html>
