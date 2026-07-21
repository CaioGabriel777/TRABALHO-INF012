import { useState } from "react";

const vazio = {
  nome: "",
  descricao: "",
  preco: "",
  quantidadeEstoque: "",
  estoqueMinimo: "",
  categoriaId: "",
  fornecedorId: "",
};

export default function ProdutoForm({
  inicial,
  categorias,
  fornecedores,
  onSalvar,
  onCancelar,
}) {
  const [form, setForm] = useState(inicial || vazio);
  const [erro, setErro] = useState(null);

  const editando = Boolean(inicial?.id);

  const mudar = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const enviar = async (e) => {
    e.preventDefault();
    setErro(null);
    try {
      await onSalvar({
        nome: form.nome,
        descricao: form.descricao,
        preco: Number(form.preco),
        quantidadeEstoque: Number(form.quantidadeEstoque),
        estoqueMinimo: Number(form.estoqueMinimo),
        categoriaId: Number(form.categoriaId),
        fornecedorId: Number(form.fornecedorId),
      });
    } catch (err) {
      setErro(err.message);
    }
  };

  return (
    <form className="form" onSubmit={enviar}>
      <h2 className="section-title" style={{ marginTop: 0 }}>
        {editando ? "Editar produto" : "Novo produto"}
      </h2>

      {erro && <div className="alert alert-error">{erro}</div>}

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
          required
        />
      </div>

      <div className="field-row">
        <div className="field">
          <label>Preço (R$)</label>
          <input
            name="preco"
            type="number"
            step="0.01"
            min="0"
            value={form.preco}
            onChange={mudar}
            required
          />
        </div>
        <div className="field">
          <label>Quantidade em estoque</label>
          <input
            name="quantidadeEstoque"
            type="number"
            min="0"
            value={form.quantidadeEstoque}
            onChange={mudar}
            required
          />
        </div>
        <div className="field">
          <label>Estoque mínimo</label>
          <input
            name="estoqueMinimo"
            type="number"
            min="0"
            value={form.estoqueMinimo}
            onChange={mudar}
            required
          />
        </div>
      </div>

      <div className="field-row">
        <div className="field">
          <label>Categoria</label>
          <select
            name="categoriaId"
            value={form.categoriaId}
            onChange={mudar}
            required
          >
            <option value="">Selecione...</option>
            {categorias.map((c) => (
              <option key={c.id} value={c.id}>
                {c.nome}
              </option>
            ))}
          </select>
        </div>
        <div className="field">
          <label>Fornecedor</label>
          <select
            name="fornecedorId"
            value={form.fornecedorId}
            onChange={mudar}
            required
          >
            <option value="">Selecione...</option>
            {fornecedores.map((f) => (
              <option key={f.id} value={f.id}>
                {f.nome}
              </option>
            ))}
          </select>
        </div>
      </div>

      <div className="form-actions">
        <button type="submit" className="btn">
          {editando ? "Salvar alterações" : "Cadastrar"}
        </button>
        <button type="button" className="btn btn-outline" onClick={onCancelar}>
          Cancelar
        </button>
      </div>
    </form>
  );
}
