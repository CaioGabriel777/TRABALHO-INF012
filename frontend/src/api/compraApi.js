import { comprasApi, listaVaziaSe404 } from "./axiosConfig";

export const listarCompras = () =>
  listaVaziaSe404(comprasApi.get("/compras").then((r) => r.data));

export const buscarCompra = (id) =>
  comprasApi.get(`/compras/${id}`).then((r) => r.data);

export const historicoPorCliente = (clienteId) =>
  comprasApi.get(`/compras/cliente/${clienteId}`).then((r) => r.data);

export const registrarCompra = (dados) =>
  comprasApi.post("/compras", dados).then((r) => r.data);

export const cancelarCompra = (id) =>
  comprasApi.put(`/compras/${id}/cancelar`).then((r) => r.data);
