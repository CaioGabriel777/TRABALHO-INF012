import { useState } from "react";
import { useClientes } from "../hooks/useClientes";
import {
  criarCliente,
  atualizarCliente,
  removerCliente,
} from "../api/clienteApi";
import ClienteList from "../components/cliente/ClienteList";
import ClienteForm from "../components/cliente/ClienteForm";

export default function ClientesPage() {
  const { clientes, carregando, erro, recarregar } = useClientes();
  const [mostrarForm, setMostrarForm] = useState(false);
  const [editando, setEditando] = useState(null);

  const novo = () => {
    setEditando(null);
    setMostrarForm(true);
  };

  const editar = (cliente) => {
    setEditando(cliente);
    setMostrarForm(true);
  };

  const salvar = async (dados) => {
    if (editando?.id) {
      await atualizarCliente(editando.id, dados);
    } else {
      await criarCliente(dados);
    }
    setMostrarForm(false);
    setEditando(null);
    recarregar();
  };

  const remover = async (cliente) => {
    if (!window.confirm(`Remover o cliente "${cliente.nome}"?`)) return;
    await removerCliente(cliente.id);
    recarregar();
  };

  return (
    <div>
      <div className="page-head">
        <h1>Clientes</h1>
        {!mostrarForm && (
          <button className="btn" onClick={novo}>
            + Novo cliente
          </button>
        )}
      </div>

      {mostrarForm ? (
        <ClienteForm
          inicial={editando}
          onSalvar={salvar}
          onCancelar={() => {
            setMostrarForm(false);
            setEditando(null);
          }}
        />
      ) : (
        <>
          {erro && <div className="alert alert-error">{erro}</div>}
          {carregando ? (
            <div className="loading">Carregando...</div>
          ) : (
            <ClienteList
              clientes={clientes}
              onEditar={editar}
              onRemover={remover}
            />
          )}
        </>
      )}
    </div>
  );
}
