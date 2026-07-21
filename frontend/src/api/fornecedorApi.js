import { inventoryApi, listaVaziaSe404 } from "./axiosConfig";

export const listarFornecedores = () =>
  listaVaziaSe404(inventoryApi.get("/fornecedores").then((r) => r.data));

export const buscarFornecedor = (id) =>
  inventoryApi.get(`/fornecedores/${id}`).then((r) => r.data);

export const criarFornecedor = (dados) =>
  inventoryApi.post("/fornecedores", dados).then((r) => r.data);

export const atualizarFornecedor = (id, dados) =>
  inventoryApi.put(`/fornecedores/${id}`, dados).then((r) => r.data);

export const removerFornecedor = (id) =>
  inventoryApi.delete(`/fornecedores/${id}`);
