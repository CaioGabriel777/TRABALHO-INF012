const moeda = (v) =>
  Number(v).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

const data = (d) => (d ? new Date(d).toLocaleString("pt-BR") : "—");

const statusClasse = {
  PENDENTE: "warn",
  CONFIRMADA: "ok",
  CONCLUIDA: "ok",
  CANCELADA: "off",
};

const entregaLabel = {
  RETIRADA_LOJA: "Retirada na loja",
  ENTREGA_DOMICILIO: "Entrega em domicílio",
};

export default function CompraList({ compras, clientesPorId, onConfirmar, onConcluir, onCancelar }) {
  if (compras.length === 0) {
    return <div className="empty">Nenhuma compra registrada.</div>;
  }

  const renderAcoes = (c) => {
    if (c.status === "PENDENTE") {
      return (
        <select
          className="action-select"
          value=""
          onChange={(e) => {
            if (e.target.value === "confirmar") onConfirmar(c);
            if (e.target.value === "cancelar") onCancelar(c);
          }}
        >
          <option value="" disabled>Opções...</option>
          <option value="confirmar">Confirmar</option>
          <option value="cancelar">Cancelar</option>
        </select>
      );
    }
    if (c.status === "CONFIRMADA") {
      return (
        <select
          className="action-select"
          value=""
          onChange={(e) => {
            if (e.target.value === "concluir") onConcluir(c);
            if (e.target.value === "cancelar") onCancelar(c);
          }}
        >
          <option value="" disabled>Opções...</option>
          <option value="concluir">Concluir</option>
          <option value="cancelar">Cancelar</option>
        </select>
      );
    }
    return <span className="muted">—</span>;
  };

  return (
    <div className="card">
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Cliente</th>
            <th>Status</th>
            <th>Entrega</th>
            <th>Itens</th>
            <th>Total</th>
            <th>Data</th>
            <th style={{ width: 180 }}>Ações</th>
          </tr>
        </thead>
        <tbody>
          {compras.map((c) => (
            <tr key={c.id}>
              <td>{c.id}</td>
              <td>{clientesPorId[c.clienteId] || `Cliente ${c.clienteId}`}</td>
              <td>
                <span className={`badge ${statusClasse[c.status] || ""}`}>
                  {c.status}
                </span>
              </td>
              <td>{entregaLabel[c.tipoEntrega] || c.tipoEntrega}</td>
              <td>{c.itens?.length || 0}</td>
              <td>{moeda(c.valorTotal)}</td>
              <td>{data(c.dataCriacao)}</td>
              <td>{renderAcoes(c)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
