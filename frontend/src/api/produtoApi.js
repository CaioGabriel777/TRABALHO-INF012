import { inventoryApi, listaVaziaSe404 } from "./axiosConfig";

export const listarProdutos = () =>
  listaVaziaSe404(inventoryApi.get("/produtos").then((r) => r.data));

export const buscarProduto = (id) =>
  inventoryApi.get(`/produtos/${id}`).then((r) => r.data);

export const criarProduto = (dados) =>
  inventoryApi.post("/produtos", dados).then((r) => r.data);

export const atualizarProduto = (id, dados) =>
  inventoryApi.put(`/produtos/${id}`, dados).then((r) => r.data);

export const removerProduto = (id) =>
  inventoryApi.delete(`/produtos/${id}`);
