import { useEffect, useState, useCallback } from "react";
import {
  listarCompras,
  registrarCompra,
  cancelarCompra,
  confirmarCompra,
  concluirCompra,
} from "../api/compraApi";
import { listarClientes } from "../api/clienteApi";
import { listarProdutos } from "../api/produtoApi";
import CompraList from "../components/compra/CompraList";
import CompraForm from "../components/compra/CompraForm";

export default function ComprasPage() {
  const [compras, setCompras] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [produtos, setProdutos] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState(null);
  const [mostrarForm, setMostrarForm] = useState(false);

  const carregar = useCallback(async () => {
    setCarregando(true);
    setErro(null);
    try {
      const [c, cli, prod] = await Promise.all([
        listarCompras(),
        listarClientes(),
        listarProdutos(),
      ]);
      setCompras(c);
      setClientes(cli);
      setProdutos(prod);
    } catch (e) {
      setErro(e.message);
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    carregar();
  }, [carregar]);

  const clientesPorId = clientes.reduce((acc, c) => {
    acc[c.id] = c.nome;
    return acc;
  }, {});

  const salvar = async (dados) => {
    await registrarCompra(dados);
    setMostrarForm(false);
    carregar();
  };

  const confirmar = async (compra) => {
    if (!window.confirm(`Confirmar a compra #${compra.id}?`)) return;
    try {
      await confirmarCompra(compra.id);
      carregar();
    } catch (err) {
      setErro(err.message);
    }
  };

  const concluir = async (compra) => {
    if (!window.confirm(`Concluir a compra #${compra.id}?`)) return;
    try {
      await concluirCompra(compra.id);
      carregar();
    } catch (err) {
      setErro(err.message);
    }
  };

  const cancelar = async (compra) => {
    if (!window.confirm(`Cancelar a compra #${compra.id}?`)) return;
    try {
      await cancelarCompra(compra.id);
      carregar();
    } catch (err) {
      setErro(err.message);
    }
  };

  return (
    <div>
      <div className="page-head">
        <h1>Compras</h1>
        {!mostrarForm && (
          <button className="btn" onClick={() => setMostrarForm(true)}>
            + Nova compra
          </button>
        )}
      </div>

      {erro && <div className="alert alert-error">{erro}</div>}

      {mostrarForm ? (
        <CompraForm
          clientes={clientes}
          produtos={produtos}
          onSalvar={salvar}
          onCancelar={() => setMostrarForm(false)}
        />
      ) : carregando ? (
        <div className="loading">Carregando...</div>
      ) : (
        <CompraList
          compras={compras}
          clientesPorId={clientesPorId}
          onConfirmar={confirmar}
          onConcluir={concluir}
          onCancelar={cancelar}
        />
      )}
    </div>
  );
}
