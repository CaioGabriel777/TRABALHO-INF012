import axios from "axios";

// Cada microserviço roda em uma porta diferente, entao criamos uma
// instancia de axios por servico a partir das URLs definidas no .env
export function criarApi(baseURL) {
  const api = axios.create({
    baseURL,
    headers: { "Content-Type": "application/json" },
  });

  // Traduz o erro do backend para uma mensagem simples
  api.interceptors.response.use(
    (response) => response,
    (error) => {
      const data = error.response?.data;
      const mensagem =
        data?.mensagem ||
        data?.message ||
        data?.erro ||
        data?.error ||
        (typeof data === "string" ? data : null) ||
        error.message ||
        "Erro ao comunicar com o servidor";
      const erro = new Error(mensagem);
      erro.status = error.response?.status;
      return Promise.reject(erro);
    }
  );

  return api;
}

// Alguns endpoints de listagem retornam 404 quando nao ha registros.
// Para a UI, isso equivale a uma lista vazia.
export function listaVaziaSe404(promise) {
  return promise.catch((e) => {
    if (e.status === 404) return [];
    throw e;
  });
}

export const inventoryApi = criarApi(
  process.env.REACT_APP_INVENTORY_URL || "http://localhost:8081"
);
export const clienteApi = criarApi(
  process.env.REACT_APP_CLIENTE_URL || "http://localhost:8082"
);
export const comprasApi = criarApi(
  process.env.REACT_APP_COMPRAS_URL || "http://localhost:8083"
);
