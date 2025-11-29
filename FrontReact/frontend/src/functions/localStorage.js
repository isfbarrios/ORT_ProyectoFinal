export const KEY = "auth";
export const SESSION_KEY = "restaurant-session-id";
export const API_URL = "http://localhost:8080/api";

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