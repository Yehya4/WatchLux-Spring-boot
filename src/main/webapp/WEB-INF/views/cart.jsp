<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mon Panier - Mini E-Commerce</title>
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
                <h1 class="page-title">Mon Panier</h1>
                <p style="color: var(--text-secondary); margin-top: 0.25rem;">Gérez vos articles avant de finaliser votre commande.</p>
            </div>
            <c:if test="${not empty panier.lignePaniers}">
                <a href="${pageContext.request.contextPath}/panier/clear" class="btn btn-sm btn-danger">Vider le Panier</a>
            </c:if>
        </div>

        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                Une erreur est survenue lors de la validation : ${param.error}
            </div>
        </c:if>

        <c:choose>
            <c:when test="${empty panier.lignePaniers}">
                <div style="text-align: center; padding: 5rem 2rem; background-color: var(--card-background); border-radius: var(--border-radius); border: 1px solid var(--border-color);">
                    <div style="font-size: 4rem; margin-bottom: 1.5rem;">🛒</div>
                    <h3 style="color: var(--secondary-color); margin-bottom: 0.5rem; font-size: 1.5rem;">Votre panier est vide</h3>
                    <p style="color: var(--text-secondary); margin-bottom: 2rem;">Il semble que vous n'ayez pas encore d'articles.</p>
                    <a href="${pageContext.request.contextPath}/produits" class="btn btn-primary">Découvrir la boutique</a>
                </div>
            </c:when>
            <c:otherwise>
                <div style="display: grid; grid-template-columns: 2fr 1fr; gap: 2rem; align-items: flex-start;">
                    
                    <!-- CART LINES TABLE -->
                    <div class="table-responsive" style="margin-bottom: 0;">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Produit</th>
                                    <th>Prix Unitaire</th>
                                    <th>Quantité</th>
                                    <th>Total</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${panier.lignePaniers}" var="lp">
                                    <tr>
                                        <td>
                                            <div style="display: flex; align-items: center; gap: 1rem;">
                                                <div style="font-size: 1.5rem; background-color: #f1f5f9; width: 40px; height: 40px; display: flex; align-items: center; justify-content: center; border-radius: 8px;">📦</div>
                                                <div>
                                                    <div style="font-weight: 600; color: var(--secondary-color);">${lp.produits.nom}</div>
                                                    <div style="font-size: 0.75rem; color: var(--text-secondary);">${lp.produits.categorie.nom}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td>${lp.produits.prix} DT</td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/panier/update" method="POST" style="margin: 0;" class="qty-selector">
                                                <input type="hidden" name="lignePanierId" value="${lp.id}">
                                                <input type="number" name="quantite" value="${lp.quantite}" min="1" max="${lp.produits.quantite}" class="qty-input" onchange="this.form.submit()">
                                            </form>
                                        </td>
                                        <td style="font-weight: 600; color: var(--secondary-color);">${lp.produits.prix * lp.quantite} DT</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/panier/remove/${lp.id}" style="color: var(--danger-color); text-decoration: none; font-size: 0.9rem; font-weight: 600;">Supprimer</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- ORDER SUMMARY -->
                    <div>
                        <div class="card" style="position: sticky; top: 100px;">
                            <div class="card-body" style="padding: 2rem;">
                                <h3 style="margin-bottom: 1.5rem; font-size: 1.25rem; color: var(--secondary-color); border-bottom: 1px solid var(--border-color); padding-bottom: 0.75rem;">Résumé</h3>
                                
                                <div style="display: flex; justify-content: space-between; margin-bottom: 1rem; font-size: 0.95rem; color: var(--text-secondary);">
                                    <span>Sous-total</span>
                                    <span>${total} DT</span>
                                </div>
                                <div style="display: flex; justify-content: space-between; margin-bottom: 1.5rem; font-size: 0.95rem; color: var(--text-secondary);">
                                    <span>Livraison</span>
                                    <span style="color: var(--success-color); font-weight: 600;">Gratuit</span>
                                </div>

                                <div style="display: flex; justify-content: space-between; margin-bottom: 2rem; font-size: 1.25rem; font-weight: 700; color: var(--secondary-color); border-top: 1px solid var(--border-color); padding-top: 1rem;">
                                    <span>Total</span>
                                    <span style="color: var(--primary-color);">${total} DT</span>
                                </div>

                                <a href="${pageContext.request.contextPath}/commande/checkout" class="btn btn-primary" style="width: 100%; padding: 0.8rem; font-size: 1rem;">Valider ma commande</a>
                                <a href="${pageContext.request.contextPath}/produits" class="btn btn-secondary" style="width: 100%; margin-top: 0.75rem; padding: 0.8rem; font-size: 1rem; color: var(--text-primary); border: 1px solid var(--border-color); background-color: transparent;">Continuer mes achats</a>
                            </div>
                        </div>
                    </div>

                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
