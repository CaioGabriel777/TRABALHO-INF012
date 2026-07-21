import { useEffect, useState, useCallback } from "react";
import {
  listarCategorias,
  criarCategoria,
  atualizarCategoria,
  removerCategoria,
} from "../api/categoriaApi";

const vazio = { nome: "", descricao: "" };

export default function CategoriasPage() {
  const [categorias, setCategorias] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState(null);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [form, setForm] = useState(vazio);
  const [editId, setEditId] = useState(null);

  const carregar = useCallback(async () => {
    setCarregando(true);
    setErro(null);
    try {
      setCategorias(await listarCategorias());
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

  const abrirEdicao = (c) => {
    setForm({ nome: c.nome, descricao: c.descricao || "" });
    setEditId(c.id);
    setMostrarForm(true);
  };

  const mudar = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const salvar = async (e) => {
    e.preventDefault();
    setErro(null);
    try {
      if (editId) await atualizarCategoria(editId, form);
      else await criarCategoria(form);
      setMostrarForm(false);
      carregar();
    } catch (err) {
      setErro(err.message);
    }
  };

  const remover = async (c) => {
    if (!window.confirm(`Remover a categoria "${c.nome}"?`)) return;
    try {
      await removerCategoria(c.id);
      carregar();
    } catch (err) {
      setErro(err.message);
    }
  };

  return (
    <div>
      <div className="page-head">
        <h1>Categorias</h1>
        {!mostrarForm && (
          <button className="btn" onClick={abrirNovo}>
            + Nova categoria
          </button>
        )}
      </div>

      {erro && <div className="alert alert-error">{erro}</div>}

      {mostrarForm ? (
        <form className="form" onSubmit={salvar}>
          <h2 className="section-title" style={{ marginTop: 0 }}>
            {editId ? "Editar categoria" : "Nova categoria"}
          </h2>
          <div className="field">
            <label>Nome</label>
            <input name="nome" value={form.nome} onChange={mudar} required />
          </div>
          <div className="field">
            <label>Descrição</label>
            <textarea
              name="descricao"
              rows="2"
              value={form.descricao}
              onChange={mudar}
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
      ) : categorias.length === 0 ? (
        <div className="empty">Nenhuma categoria cadastrada.</div>
      ) : (
        <div className="card">
          <table>
            <thead>
              <tr>
                <th>Nome</th>
                <th>Descrição</th>
                <th style={{ width: 160 }}>Ações</th>
              </tr>
            </thead>
            <tbody>
              {categorias.map((c) => (
                <tr key={c.id}>
                  <td>{c.nome}</td>
                  <td>{c.descricao || "—"}</td>
                  <td>
                    <div className="row-actions">
                      <button
                        className="btn btn-outline btn-sm"
                        onClick={() => abrirEdicao(c)}
                      >
                        Editar
                      </button>
                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => remover(c)}
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
