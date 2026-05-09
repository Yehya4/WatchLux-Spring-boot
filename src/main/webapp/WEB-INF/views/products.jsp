<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Boutique - Mini E-Commerce</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <!-- NAVBAR SIMPLE (UNIQUEMENT CLIENT) -->
    <nav class="navbar">
        <a href="${pageContext.request.contextPath}/" class="navbar-brand">MiniShop</a>
        <div class="navbar-links">
            <a href="${pageContext.request.contextPath}/produits" class="navbar-link active">Boutique</a>
            <a href="${pageContext.request.contextPath}/panier" class="navbar-link">Mon Panier</a>
            <a href="${pageContext.request.contextPath}/commandes" class="navbar-link">Mes Commandes</a>
            <span class="user-badge" style="background-color: var(--primary-color);">
                👤 ${sessionScope.currentUser.prenom} ${sessionScope.currentUser.nom}
            </span>
        </div>
    </nav>

    <!-- BOUTIQUE -->
    <div class="container">
        <div class="page-header">
            <div>
                <h1 class="page-title">Notre Boutique</h1>
                <p style="color: var(--text-secondary); margin-top: 0.25rem;">Découvrez notre large gamme de produits et achetez en toute simplicité.</p>
            </div>
        </div>

        <!-- BARRE DE RECHERCHE ET FILTRE SIMPLE -->
        <div class="search-filter-bar">
            <form action="${pageContext.request.contextPath}/produits" method="GET" class="search-input-group">
                <input type="text" name="search" class="form-control" placeholder="Rechercher par nom..." value="${searchKeyword}">
                <button type="submit" class="btn btn-primary">Rechercher</button>
            </form>
            <form action="${pageContext.request.contextPath}/produits" method="GET" style="display: flex; gap: 0.5rem; align-items: center;">
                <select name="categoryId" class="form-control" onchange="this.form.submit()" style="width: 220px;">
                    <option value="">Toutes les catégories</option>
                    <c:forEach items="${categories}" var="cat">
                        <option value="${cat.id}" ${cat.id == selectedCategoryId ? 'selected' : ''}>${cat.nom}</option>
                    </c:forEach>
                </select>
            </form>
        </div>

        <!-- GRILLE DES PRODUITS -->
        <c:choose>
            <c:when test="${empty products}">
                <div style="text-align: center; padding: 4rem; background-color: var(--card-background); border-radius: var(--border-radius); border: 1px solid var(--border-color);">
                    <h3 style="color: var(--text-secondary); margin-bottom: 1rem;">Aucun produit disponible</h3>
                    <a href="${pageContext.request.contextPath}/produits" class="btn btn-primary" style="margin-top: 1rem;">Voir tous les produits</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="grid">
                    <c:forEach items="${products}" var="p">
                        <div class="card">
                            <div style="background-color: #f1f5f9; height: 180px; display: flex; align-items: center; justify-content: center; position: relative;">
                                <div style="font-size: 3rem;">📦</div>
                                <span class="badge badge-info" style="position: absolute; top: 1rem; right: 1rem;">${p.categorie.nom}</span>
                            </div>
                            <div class="card-body">
                                <h3 class="card-title">${p.nom}</h3>
                                <p class="card-desc">${p.description}</p>
                                <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 1rem;">
                                    <div style="font-size: 0.85rem; color: var(--text-secondary);">
                                        Stock: 
                                        <c:choose>
                                            <c:when test="${p.quantite > 0}">
                                                <span style="color: var(--success-color); font-weight: 600;">${p.quantite} dispo</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: var(--danger-color); font-weight: 600;">Rupture de stock</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <span class="card-price" style="font-size: 1.15rem;">${p.prix} DT</span>
                                <c:choose>
                                    <c:when test="${p.quantite > 0}">
                                        <form action="${pageContext.request.contextPath}/panier/add" method="POST" style="margin: 0;">
                                            <input type="hidden" name="produitId" value="${p.id}">
                                            <input type="hidden" name="quantite" value="1">
                                            <button type="submit" class="btn btn-primary btn-sm">Ajouter au panier</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-secondary btn-sm" disabled style="opacity: 0.5; cursor: not-allowed;">Indisponible</button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>
