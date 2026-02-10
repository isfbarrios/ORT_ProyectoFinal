import { jwtDecode } from 'jwt-decode';

export async function apiFetch(url, options = {}) {
  const token = localStorage.getItem("token");

  const response = await fetch(url, {
    ...options,
    headers: {
      ...options.headers,
      Authorization: token ? `Bearer ${token}` : ""
    }
  });

  if (response.status === 401) {
    localStorage.clear();
    window.location.href = "/";
    throw new Error("Unauthorized");
  }

  return response;
}


export function isTokenExpired(token) {
  try {
    const decoded = jwtDecode(token);

    if (!decoded.exp) return true;

    const now = Date.now() / 1000;
    return decoded.exp < now;
  }
  catch (error) {
    console.error("Error al decodificar token:", error);
    return true;
  }
}