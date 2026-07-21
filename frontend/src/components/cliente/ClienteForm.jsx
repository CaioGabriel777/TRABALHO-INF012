import { useState } from "react";

const vazio = { nome: "", cpf: "", email: "", telefone: "" };

export default function ClienteForm({ inicial, onSalvar, onCancelar }) {
  const [form, setForm] = useState(inicial || vazio);
  const [erro, setErro] = useState(null);

  const editando = Boolean(inicial?.id);
  const mudar = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const enviar = async (e) => {
    e.preventDefault();
    setErro(null);
    try {
      await onSalvar({
        nome: form.nome,
        cpf: form.cpf,
        email: form.email,
        telefone: form.telefone,
      });
    } catch (err) {
      setErro(err.message);
    }
  };

  return (
    <form className="form" onSubmit={enviar}>
      <h2 className="section-title" style={{ marginTop: 0 }}>
        {editando ? "Editar cliente" : "Novo cliente"}
      </h2>

      {erro && <div className="alert alert-error">{erro}</div>}

      <div className="field">
        <label>Nome</label>
        <input name="nome" value={form.nome} onChange={mudar} required />
      </div>

      <div className="field-row">
        <div className="field">
          <label>CPF (somente números)</label>
          <input
            name="cpf"
            value={form.cpf}
            onChange={mudar}
            maxLength="11"
            required
          />
        </div>
        <div className="field">
          <label>Telefone</label>
          <input name="telefone" value={form.telefone} onChange={mudar} />
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
          {editando ? "Salvar alterações" : "Cadastrar"}
        </button>
        <button type="button" className="btn btn-outline" onClick={onCancelar}>
          Cancelar
        </button>
      </div>
    </form>
  );
}
