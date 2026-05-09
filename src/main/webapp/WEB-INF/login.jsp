<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion - Mini E-Commerce</title>
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body style="display: flex; align-items: center; justify-content: center; min-height: 100vh; background: radial-gradient(circle at top right, #f8fafc, #e2e8f0);">

    <div class="auth-container">
        <div class="auth-header">
            <div class="auth-logo">MiniShop</div>
            <div class="auth-subtitle">Plateforme e-commerce simplifiée</div>
        </div>

        <c:if test="${param.error != null}">
            <div class="alert alert-danger">
                Utilisateur non trouvé ou invalide.
            </div>
        </c:if>
        <c:if test="${param.errorExists != null}">
            <div class="alert alert-danger">
                Un utilisateur avec cet email existe déjà.
            </div>
        </c:if>

        <!-- SELECTION DE COMPTE EXISTANT -->
        <form action="${pageContext.request.contextPath}/login" method="POST" style="margin-bottom: 2rem; border-bottom: 1px solid var(--border-color); padding-bottom: 2rem;">
            <h3 style="margin-bottom: 1rem; font-size: 1.1rem; color: var(--secondary-color);">Se connecter</h3>
            <div class="form-group">
                <label class="form-label" for="userId">Choisir un profil existant</label>
                <select name="userId" id="userId" class="form-control" required>
                    <option value="" disabled selected>-- Sélectionnez un compte --</option>
                    <c:forEach items="${users}" var="u">
                        <option value="${u.id}">${u.prenom} ${u.nom} (${u.utilisateurRole})</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary" style="width: 100%;">Accéder à l'application</button>
        </form>

        <!-- CREATION DE COMPTE -->
        <form action="${pageContext.request.contextPath}/register" method="POST">
            <h3 style="margin-bottom: 1rem; font-size: 1.1rem; color: var(--secondary-color);">Créer un nouveau compte</h3>
            <div class="form-group">
                <label class="form-label" for="prenom">Prénom</label>
                <input type="text" name="prenom" id="prenom" class="form-control" placeholder="Entrez votre prénom" required>
            </div>
            <div class="form-group">
                <label class="form-label" for="nom">Nom</label>
                <input type="text" name="nom" id="nom" class="form-control" placeholder="Entrez votre nom" required>
            </div>
            <div class="form-group">
                <label class="form-label" for="email">Adresse Email</label>
                <input type="email" name="email" id="email" class="form-control" placeholder="nom@exemple.com" required>
            </div>
            <div class="form-group">
                <label class="form-label">Rôle d'utilisateur</label>
                <div style="display: flex; gap: 1.5rem; margin-top: 0.5rem;">
                    <label style="display: flex; align-items: center; gap: 0.5rem; cursor: pointer;">
                        <input type="radio" name="role" value="CLIENT" checked style="accent-color: var(--primary-color);"> Client
                    </label>
                    <label style="display: flex; align-items: center; gap: 0.5rem; cursor: pointer;">
                        <input type="radio" name="role" value="ADMIN" style="accent-color: var(--primary-color);"> Administrateur
                    </label>
                </div>
            </div>
            <button type="submit" class="btn btn-secondary" style="width: 100%; font-weight: 700; color: white; background-color: var(--secondary-color);">Créer & Se connecter</button>
        </form>
    </div>

</body>
</html>
