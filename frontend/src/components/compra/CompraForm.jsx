import { useState } from "react";

const itemVazio = { produtoId: "", quantidade: "1", precoUnitario: "" };

export default function CompraForm({ clientes, produtos, onSalvar, onCancelar }) {
  const [clienteId, setClienteId] = useState("");
  const [tipoEntrega, setTipoEntrega] = useState("RETIRADA_LOJA");
  const [itens, setItens] = useState([{ ...itemVazio }]);
  const [erro, setErro] = useState(null);

  const mudarItem = (index, campo, valor) => {
    const novos = [...itens];
    novos[index] = { ...novos[index], [campo]: valor };
    // ao escolher o produto, sugere o preco cadastrado
    if (campo === "produtoId") {
      const p = produtos.find((x) => String(x.id) === String(valor));
      if (p) novos[index].precoUnitario = p.preco;
    }
    setItens(novos);
  };

  const adicionarItem = () => setItens([...itens, { ...itemVazio }]);
  const removerItem = (index) =>
    setItens(itens.filter((_, i) => i !== index));

  const total = itens.reduce(
    (soma, i) => soma + Number(i.quantidade || 0) * Number(i.precoUnitario || 0),
    0
  );

  const enviar = async (e) => {
    e.preventDefault();
    setErro(null);
    try {
      await onSalvar({
        clienteId: Number(clienteId),
        tipoEntrega,
        itens: itens.map((i) => ({
          produtoId: Number(i.produtoId),
          quantidade: Number(i.quantidade),
          precoUnitario: Number(i.precoUnitario),
        })),
      });
    } catch (err) {
      setErro(err.message);
    }
  };

  return (
    <form className="form" style={{ maxWidth: 760 }} onSubmit={enviar}>
      <h2 className="section-title" style={{ marginTop: 0 }}>
        Nova compra
      </h2>

      {erro && <div className="alert alert-error">{erro}</div>}

      <div className="field-row">
        <div className="field">
          <label>Cliente</label>
          <select
            value={clienteId}
            onChange={(e) => setClienteId(e.target.value)}
            required
          >
            <option value="">Selecione...</option>
            {clientes.map((c) => (
              <option key={c.id} value={c.id}>
                {c.nome}
              </option>
            ))}
          </select>
        </div>
        <div className="field">
          <label>Tipo de entrega</label>
          <select
            value={tipoEntrega}
            onChange={(e) => setTipoEntrega(e.target.value)}
          >
            <option value="RETIRADA_LOJA">Retirada na loja</option>
            <option value="ENTREGA_DOMICILIO">Entrega em domicílio</option>
          </select>
        </div>
      </div>

      <h3 className="section-title">Itens</h3>
      {itens.map((item, index) => (
        <div className="item-line" key={index}>
          <div className="field" style={{ flex: 2 }}>
            <label>Produto</label>
            <select
              value={item.produtoId}
              onChange={(e) => mudarItem(index, "produtoId", e.target.value)}
              required
            >
              <option value="">Selecione...</option>
              {produtos.map((p) => (
                <option key={p.id} value={p.id}>
                  {p.nome}
                </option>
              ))}
            </select>
          </div>
          <div className="field" style={{ width: 90 }}>
            <label>Qtd.</label>
            <input
              type="number"
              min="1"
              value={item.quantidade}
              onChange={(e) => mudarItem(index, "quantidade", e.target.value)}
              required
            />
          </div>
          <div className="field" style={{ width: 120 }}>
            <label>Preço unit.</label>
            <input
              type="number"
              step="0.01"
              min="0"
              value={item.precoUnitario}
              onChange={(e) => mudarItem(index, "precoUnitario", e.target.value)}
              required
            />
          </div>
          <button
            type="button"
            className="btn btn-danger btn-sm"
            onClick={() => removerItem(index)}
            disabled={itens.length === 1}
          >
            ×
          </button>
        </div>
      ))}

      <button type="button" className="btn btn-outline btn-sm" onClick={adicionarItem}>
        + Adicionar item
      </button>

      <div style={{ margin: "16px 0", fontWeight: 600 }}>
        Total:{" "}
        {total.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}
      </div>

      <div className="form-actions">
        <button type="submit" className="btn">
          Registrar compra
        </button>
        <button type="button" className="btn btn-outline" onClick={onCancelar}>
          Cancelar
        </button>
      </div>
    </form>
  );
}
