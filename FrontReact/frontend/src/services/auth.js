const KEY = "auth"; // --> luego lo voy a sustituir con lo que me pase springboot

export function saveAuth(auth) {
  localStorage.setItem(KEY, JSON.stringify(auth));
}

// leo la sesion guardada
export function getAuth() {
  const raw = localStorage.getItem(KEY);
  return raw ? JSON.parse(raw) : null;
}

//borro la sesion guardada en storage
export function clearAuth() {
  localStorage.removeItem(KEY);
}

// MOCK login (luego lo vamos a remplazar por fetch al backend)
export async function loginApi({ email, password }) {
  await new Promise(r => setTimeout(r, 600)); // simula latencia
  if (!email || !password) {
    const error = new Error("Credenciales incompletas");
    error.status = 400;
    throw error;
  }

  // Demo: va acepta cualquier email/pass
  return { token: "fake-jwt-token", user: { id: 1, email } };


  /*

    cuando conectemos con springboot loginApi =>

    const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
    });

    const data = await res.json();
    return data;

  */


 
}
