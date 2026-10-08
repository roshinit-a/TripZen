// ===================================
// TripZen - Global Auth Utilities
// ===================================

const API_BASE = 'http://localhost:8080/api';

// ---- Token Management ----
function saveAuth(data) {
    localStorage.setItem('tripzen_token', data.token);
    localStorage.setItem('tripzen_user', JSON.stringify({
        id: data.userId,
        name: data.name,
        email: data.email,
        role: data.role
    }));
}

function getToken() {
    return localStorage.getItem('tripzen_token');
}

function getUser() {
    const u = localStorage.getItem('tripzen_user');
    return u ? JSON.parse(u) : null;
}

function isLoggedIn() {
    return !!getToken();
}

function isAdmin() {
    const u = getUser();
    return u && u.role === 'ADMIN';
}

function logout() {
    localStorage.removeItem('tripzen_token');
    localStorage.removeItem('tripzen_user');
    window.location.href = '/frontend/index.html';
}

// ---- Fetch with Auth ----
async function apiGet(endpoint) {
    const res = await fetch(`${API_BASE}${endpoint}`, {
        headers: { 'Authorization': `Bearer ${getToken()}` }
    });
    if (!res.ok) throw new Error(await res.text());
    return res.json();
}

async function apiPost(endpoint, body, requireAuth = false) {
    const headers = { 'Content-Type': 'application/json' };
    if (requireAuth) headers['Authorization'] = `Bearer ${getToken()}`;
    const res = await fetch(`${API_BASE}${endpoint}`, {
        method: 'POST',
        headers,
        body: JSON.stringify(body)
    });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ error: 'Request failed' }));
        throw new Error(err.error || 'Request failed');
    }
    return res.json();
}

async function apiPut(endpoint, body = null) {
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getToken()}`
    };
    const res = await fetch(`${API_BASE}${endpoint}`, {
        method: 'PUT',
        headers,
        body: body ? JSON.stringify(body) : null
    });
    if (!res.ok) {
        const err = await res.json().catch(() => ({ error: 'Request failed' }));
        throw new Error(err.error || 'Request failed');
    }
    return res.json();
}

async function apiDelete(endpoint) {
    const res = await fetch(`${API_BASE}${endpoint}`, {
        method: 'DELETE',
        headers: { 'Authorization': `Bearer ${getToken()}` }
    });
    if (!res.ok) throw new Error('Delete failed');
    return res.json();
}

// ---- Format Helpers ----
function formatCurrency(amount) {
    return '₹' + Number(amount).toLocaleString('en-IN');
}

function formatDate(dateStr) {
    if (!dateStr) return '-';
    return new Date(dateStr).toLocaleDateString('en-IN', {
        day: '2-digit', month: 'long', year: 'numeric'
    });
}

function getStatusBadge(status) {
    const map = {
        'CONFIRMED': 'badge-success',
        'PENDING':   'badge-warning',
        'CANCELLED': 'badge-danger'
    };
    return `<span class="badge ${map[status] || 'badge-primary'}">${status}</span>`;
}

function showAlert(containerId, message, type = 'success') {
    const el = document.getElementById(containerId);
    if (!el) return;
    el.innerHTML = `<div class="alert alert-${type}">${message}</div>`;
    setTimeout(() => { el.innerHTML = ''; }, 4000);
}

// ---- Navbar update based on login state ----
function updateNavbar() {
    const user = getUser();
    const loginLink = document.getElementById('navLogin');
    const userMenu = document.getElementById('navUser');

    if (user && loginLink) {
        loginLink.style.display = 'none';
    }
    if (user && userMenu) {
        userMenu.style.display = 'flex';
        userMenu.innerHTML = `
            <span style="color:#555;font-weight:500">👋 ${user.name}</span>
            ${user.role === 'ADMIN'
                ? '<a href="/frontend/admin/admin-dashboard.html" class="btn btn-primary" style="padding:8px 16px;font-size:0.85rem">Admin Panel</a>'
                : '<a href="/frontend/my-bookings.html" style="color:#555">My Bookings</a>'}
            <button class="btn btn-outline" onclick="logout()" style="padding:8px 16px;font-size:0.85rem">Logout</button>
        `;
    }
}

// ---- Generate star rating HTML ----
function renderStars(rating) {
    const full = Math.floor(rating);
    const half = rating % 1 >= 0.5 ? 1 : 0;
    let stars = '★'.repeat(full) + (half ? '½' : '') + '☆'.repeat(5 - full - half);
    return `<span class="stars">${stars}</span> <small>(${rating})</small>`;
}

// Auto-update navbar on every page
document.addEventListener('DOMContentLoaded', updateNavbar);
