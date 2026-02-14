const API_BASE = "/api";

function buildOptions(method, body, jwt) {
  const options = { method, headers: { "Content-Type": "application/json" } };
  if (jwt) options.headers.Authorization = `Bearer ${jwt}`;
  if (method !== "GET" && body) options.body = JSON.stringify(body);
  return options;
}

function handleUnauthorized() {
  const curCity = localStorage.getItem("curCity");
  const curDate = localStorage.getItem("curDate");
  localStorage.removeItem("solAndRJwt");
  localStorage.removeItem("memberId");
  window.location.href = curCity && curDate ? `/browse/city/${curCity}/date/${curDate}` : "/";
}

async function handleResponse(response) {
  if (response.status === 401) {
    handleUnauthorized();
    throw new Error("Unauthorized");
  }
  if (response.status === 204 || response.status === 201) return null;
  if (!response.ok) throw new Error(`HTTP ${response.status}`);
  const contentType = response.headers.get("content-type");
  if (contentType?.includes("application/json")) return response.json();
  return null;
}

export async function apiClient({ path, method = "GET", body = null, jwt = null }) {
  try {
    const response = await fetch(`${API_BASE}/${path}`, buildOptions(method, body, jwt));
    return await handleResponse(response);
  } catch (error) {
    console.error("API error:", { path, method, message: error.message });
    throw error;
  }
}