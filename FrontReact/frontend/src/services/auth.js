import {
  KEY,
  SESSION_KEY,
  API_URL,
  saveToLocalStorage,
  getFromLocalStorage,
  removeFromLocalStorage
} from "../functions/localStorage"

// guardo la session del usuario
export function saveAuth(data) {
  const session = {
    isLogged: true,
    user: data.user ?? data,
  };

  saveToLocalStorage(KEY, session);

  // si el back me  devolvió sessionId, lo guardo para el carrito
  const token = data?.token;
  if (token) {
    console.log('saveAuth.sessionId: ' + token);

    saveToLocalStorage(SESSION_KEY, String(token));
    //TODO: Si no tenemos el token, deberiamos cerrar sesion
  }
}

// leo la sesion guardada
export function getAuth() {
  return getFromLocalStorage(KEY);
}

//borro la sesion guardada en storage
export function clearAuth() {
  removeFromLocalStorage(KEY);
  removeFromLocalStorage(SESSION_KEY);
}

// MOCK login (luego lo vamos a remplazar por fetch al backend)
export async function loginApi({ email, password }) {
  const res = await fetch(API_URL + "/users/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      mail: email,
      password: password
    })
  });

  let data;

  try {
    data = await res.json();
  }
  catch {
    throw new Error("Respuesta inválida del servidor");
  }

  if (!res.ok) {
    throw new Error(data.message || "Credenciales inválidas");
  }

  return data;
}
