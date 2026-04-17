(function () {
  const STORAGE_TOKEN = "tinycorp_token";
  const STORAGE_BASE = "tinycorp_base_url";

  function getBaseUrl() {
    return localStorage.getItem(STORAGE_BASE) || "http://localhost:8080";
  }

  function setBaseUrl(url) {
    localStorage.setItem(STORAGE_BASE, url || "http://localhost:8080");
  }

  function getToken() {
    return localStorage.getItem(STORAGE_TOKEN) || "";
  }

  function setToken(token) {
    localStorage.setItem(STORAGE_TOKEN, token || "");
  }

  function clearToken() {
    localStorage.removeItem(STORAGE_TOKEN);
  }

  async function request(path, method, body, auth) {
    const headers = { "Content-Type": "application/json" };
    if (auth !== false && getToken()) {
      headers.Authorization = `Bearer ${getToken()}`;
    }

    const response = await fetch(`${getBaseUrl()}${path}`, {
      method: method || "GET",
      headers,
      body: body ? JSON.stringify(body) : undefined
    });

    let payload;
    const text = await response.text();
    try {
      payload = text ? JSON.parse(text) : {};
    } catch {
      payload = { raw: text };
    }

    if (!response.ok) {
      const message = payload?.details?.join(", ") || payload?.error || `HTTP ${response.status}`;
      throw new Error(message);
    }

    return payload;
  }

  function renderPager(targetId, page, onChange) {
    const target = document.getElementById(targetId);
    if (!target) {
      return;
    }
    target.innerHTML = "";

    const prev = document.createElement("button");
    prev.textContent = "Previous";
    prev.disabled = page.first;
    prev.onclick = () => onChange(Math.max(0, page.page - 1));

    const info = document.createElement("div");
    info.textContent = `Page ${page.page + 1} / ${Math.max(page.totalPages, 1)} | Total: ${page.totalElements}`;

    const next = document.createElement("button");
    next.textContent = "Next";
    next.disabled = page.last;
    next.onclick = () => onChange(page.page + 1);

    target.appendChild(prev);
    target.appendChild(info);
    target.appendChild(next);
  }

  function setStatus(id, message, isError) {
    const el = document.getElementById(id);
    if (!el) {
      return;
    }
    el.className = `status ${isError ? "err" : "ok"}`;
    el.textContent = message || "";
  }

  function showJson(id, data) {
    const el = document.getElementById(id);
    if (el) {
      el.textContent = JSON.stringify(data || {}, null, 2);
    }
  }

  window.App = {
    getBaseUrl,
    setBaseUrl,
    getToken,
    setToken,
    clearToken,
    request,
    renderPager,
    setStatus,
    showJson
  };
})();
