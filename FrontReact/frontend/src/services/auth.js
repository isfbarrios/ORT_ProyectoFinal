const KEY = "auth"; // --> luego lo voy a sustituir con lo que me pase springboot
const BASE_URL = "http://localhost:8080";

export function saveAuth(data) {
  
  const session = {
    isLogged: true,
    user: data.user ?? data, 
  };

  localStorage.setItem(KEY, JSON.stringify(session));
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
  console.log('email', email);
  console.log('password', password);
  const res = await fetch(BASE_URL + "/api/users/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      mail: email,
      password: password
    })
  });

  const data = await res.json();
  return data;
}
