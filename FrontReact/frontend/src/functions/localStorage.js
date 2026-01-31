export const KEY = "auth";
export const API_TOKEN = "API_TOKEN";
export const API_REFRESH_TOKEN = "API_REFRESH_TOKEN";
export const SESSION_ID = "SESSION_ID";
export const API_URL = "http://localhost:8080/api";
export const USER_TYPE = "USER_TYPE";
//export const API_URL = "https://platofuerte.lat/api";

// GUARDAR DATO 
export function saveToLocalStorage(key, value) {
  try {
    const data = JSON.stringify(value);
    localStorage.setItem(key, data);
  }
  catch (error) {
    console.error("Error al guardar en localStorage:", error);
  }
}

// OBTENER DATO 
export function getFromLocalStorage(key) {
  try {
    const data = localStorage.getItem(key);
    return data ? JSON.parse(data) : null;
  }
  catch (error) {
    console.error("Error al cargar datos localStorage:", error);
    return null;
  }
}

//  LIMPIAR 
export function clearLocalStorage() {
  try {
    localStorage.clear();
  }
  catch (error) {
    console.error("Error al borrar localStorage:", error);
  }
}

// BORRAR DATO 
export function removeFromLocalStorage(key) {
  try {
    localStorage.removeItem(key);
  }
  catch (error) {
    console.error("Error eliminando localStorage:", error);
  }
}

export function buildFetchHeader(useToken = true) {
  const token = getFromLocalStorage("API_TOKEN");
  const headers = {
    "Content-Type": "application/json",
  };

  if (useToken && token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  return headers;
}

export async function safeJson(res) {
  const text = await res.text();
  try {
    return JSON.parse(text);
  } catch {
    return { message: text || "Respuesta inválida del servidor" };
  }
}