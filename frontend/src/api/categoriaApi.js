import { inventoryApi, listaVaziaSe404 } from "./axiosConfig";

export const listarCategorias = () =>
  listaVaziaSe404(inventoryApi.get("/categorias").then((r) => r.data));

export const buscarCategoria = (id) =>
  inventoryApi.get(`/categorias/${id}`).then((r) => r.data);

export const criarCategoria = (dados) =>
  inventoryApi.post("/categorias", dados).then((r) => r.data);

export const atualizarCategoria = (id, dados) =>
  inventoryApi.put(`/categorias/${id}`, dados).then((r) => r.data);

export const removerCategoria = (id) =>
  inventoryApi.delete(`/categorias/${id}`);
