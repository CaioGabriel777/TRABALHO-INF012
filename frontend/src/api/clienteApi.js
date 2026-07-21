import { clienteApi, listaVaziaSe404 } from "./axiosConfig";

export const listarClientes = () =>
  listaVaziaSe404(clienteApi.get("/clientes").then((r) => r.data));

export const buscarCliente = (id) =>
  clienteApi.get(`/clientes/${id}`).then((r) => r.data);

export const criarCliente = (dados) =>
  clienteApi.post("/clientes", dados).then((r) => r.data);

export const atualizarCliente = (id, dados) =>
  clienteApi.put(`/clientes/${id}`, dados).then((r) => r.data);

export const removerCliente = (id) =>
  clienteApi.delete(`/clientes/${id}`);

// Enderecos ficam no mesmo microservico de cliente
export const listarEnderecos = (clienteId) =>
  clienteApi.get("/enderecos", { params: { clienteId } }).then((r) => r.data);

export const criarEndereco = (dados) =>
  clienteApi.post("/enderecos", dados).then((r) => r.data);

export const removerEndereco = (id) =>
  clienteApi.delete(`/enderecos/${id}`);
