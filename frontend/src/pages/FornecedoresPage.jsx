import { useEffect, useState, useCallback } from "react";
import {
  listarFornecedores,
  criarFornecedor,
  atualizarFornecedor,
  removerFornecedor,
} from "../api/fornecedorApi";

const vazio = { nome: "", cnpj: "", telefone: "", email: "" };

export default function FornecedoresPage() {
  const [fornecedores, setFornecedores] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState(null);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [form, setForm] = useState(vazio);
  const [editId, setEditId] = useState(null);

  const carregar = useCallback(async () => {
    setCarregando(true);
    setErro(null);
    try {
      setFornecedores(await listarFornecedores());
    } catch (e) {
      setErro(e.message);
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    carregar();
  }, [carregar]);

  const abrirNovo = () => {
    setForm(vazio);
    setEditId(null);
    setMostrarForm(true);
  };

  const abrirEdicao = (f) => {
    setForm({
      nome: f.nome,
      cnpj: f.cnpj,
      telefone: f.telefone,
      email: f.email,
    });
    setEditId(f.id);
    setMostrarForm(true);
  };

  const mudar = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const salvar = async (e) => {
    e.preventDefault();
    setErro(null);
    try {
      if (editId) await atualizarFornecedor(editId, form);
      else await criarFornecedor(form);
      setMostrarForm(false);
      carregar();
    } catch (err) {
      setErro(err.message);
    }
  };

  const remover = async (f) => {
    if (!window.confirm(`Remover o fornecedor "${f.nome}"?`)) return;
    try {
      await removerFornecedor(f.id);
      carregar();
    } catch (err) {
      setErro(err.message);
    }
  };

  return (
    <div>
      <div className="page-head">
        <h1>Fornecedores</h1>
        {!mostrarForm && (
          <button className="btn" onClick={abrirNovo}>
            + Novo fornecedor
          </button>
        )}
      </div>

      {erro && <div className="alert alert-error">{erro}</div>}

      {mostrarForm ? (
        <form className="form" onSubmit={salvar}>
          <h2 className="section-title" style={{ marginTop: 0 }}>
            {editId ? "Editar fornecedor" : "Novo fornecedor"}
          </h2>
          <div className="field">
            <label>Nome</label>
            <input name="nome" value={form.nome} onChange={mudar} required />
          </div>
          <div className="field-row">
            <div className="field">
              <label>CNPJ (somente números)</label>
              <input
                name="cnpj"
                value={form.cnpj}
                onChange={mudar}
                maxLength="14"
                required
              />
            </div>
            <div className="field">
              <label>Telefone (11 dígitos)</label>
              <input
                name="telefone"
                value={form.telefone}
                onChange={mudar}
                maxLength="11"
                required
              />
            </div>
          </div>
          <div className="field">
            <label>E-mail</label>
            <input
              name="email"
              type="email"
              value={form.email}
              onChange={mudar}
              required
            />
          </div>
          <div className="form-actions">
            <button type="submit" className="btn">
              Salvar
            </button>
            <button
              type="button"
              className="btn btn-outline"
              onClick={() => setMostrarForm(false)}
            >
              Cancelar
            </button>
          </div>
        </form>
      ) : carregando ? (
        <div className="loading">Carregando...</div>
      ) : fornecedores.length === 0 ? (
        <div className="empty">Nenhum fornecedor cadastrado.</div>
      ) : (
        <div className="card">
          <table>
            <thead>
              <tr>
                <th>Nome</th>
                <th>CNPJ</th>
                <th>Telefone</th>
                <th>E-mail</th>
                <th style={{ width: 160 }}>Ações</th>
              </tr>
            </thead>
            <tbody>
              {fornecedores.map((f) => (
                <tr key={f.id}>
                  <td>{f.nome}</td>
                  <td>{f.cnpj}</td>
                  <td>{f.telefone}</td>
                  <td>{f.email}</td>
                  <td>
                    <div className="row-actions">
                      <button
                        className="btn btn-outline btn-sm"
                        onClick={() => abrirEdicao(f)}
                      >
                        Editar
                      </button>
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => remover(f)}
                      >
                        Remover
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}
