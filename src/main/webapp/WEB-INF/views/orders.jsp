<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes Commandes - Mini E-Commerce</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

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
    <div class="container">
        <div class="page-header">
            <div>
                <h1 class="page-title">Mes Commandes</h1>
                <p style="color: var(--text-secondary); margin-top: 0.25rem;">Consultez vos commandes passées et suivez leur état.</p>
            </div>
            <a href="${pageContext.request.contextPath}/produits" class="btn btn-primary">Continuer mes achats</a>
        </div>

        <c:choose>
            <c:when test="${empty orders}">
                <div style="text-align: center; padding: 5rem 2rem; background-color: var(--card-background); border-radius: var(--border-radius); border: 1px solid var(--border-color);">
                    <div style="font-size: 4rem; margin-bottom: 1.5rem;">📦</div>
                    <h3 style="color: var(--secondary-color); margin-bottom: 0.5rem; font-size: 1.5rem;">Aucune commande</h3>
                    <p style="color: var(--text-secondary); margin-bottom: 2rem;">Vous n'avez pas encore passé de commande.</p>
                    <a href="${pageContext.request.contextPath}/produits" class="btn btn-primary">Faire des achats</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>N° Commande</th>
                                <th>Date de commande</th>
                                <th>Adresse de livraison</th>
                                <th>Montant</th>
                                <th>État Commande</th>
                                <th>État Paiement</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="c">
                                <tr>
                                    <td style="font-weight: 700; color: var(--primary-color);">#CMD-${c.id}</td>
                                    <td>
                                        <fmt:formatDate value="${c.dateCommande}" pattern="dd/MM/yyyy HH:mm" />
                                    </td>
                                    <td>
                                        <div style="max-width: 220px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" title="${c.adresseDeLivraison}">
                                            ${c.adresseDeLivraison}
                                        </div>
                                    </td>
                                    <td style="font-weight: 600; color: var(--secondary-color);">${c.montant} DT</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${c.commandestatut == 'EN_ATTENTE'}">
                                                <span class="badge badge-warning">En Attente</span>
                                            </c:when>
                                            <c:when test="${c.commandestatut == 'CONFIRMEE'}">
                                                <span class="badge badge-info">Confirmée</span>
                                            </c:when>
                                            <c:when test="${c.commandestatut == 'EN_LIVRAISON'}">
                                                <span class="badge badge-info" style="background-color: #e0f2fe; color: #0369a1;">En Livraison</span>
                                            </c:when>
                                            <c:when test="${c.commandestatut == 'LIVREE'}">
                                                <span class="badge badge-success">Livrée</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-danger">Annulée</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty c.paiements && c.paiements.statutPaiement == 'APPROUVE'}">
                                                <span class="badge badge-success">Payé</span>
                                            </c:when>
                                            <c:when test="${not empty c.paiements && c.paiements.statutPaiement == 'REFUSE'}">
                                                <span class="badge badge-danger">Refusé</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge badge-warning">Non Payé</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <div style="display: flex; gap: 0.5rem; align-items: center;">
                                            <a href="${pageContext.request.contextPath}/commandes/details/${c.id}" class="btn btn-sm btn-secondary" style="font-size: 0.75rem;">Détails</a>
                                            <c:if test="${empty c.paiements || c.paiements.statutPaiement != 'APPROUVE'}">
                                                <c:if test="${c.commandestatut != 'ANNULEE'}">
                                                    <a href="${pageContext.request.contextPath}/paiement/${c.id}" class="btn btn-sm btn-success" style="font-size: 0.75rem;">Payer</a>
                                                </c:if>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
