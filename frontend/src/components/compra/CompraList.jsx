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

export default function CompraList({ compras, clientesPorId, onCancelar }) {
  if (compras.length === 0) {
    return <div className="empty">Nenhuma compra registrada.</div>;
  }

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
            <th style={{ width: 110 }}>Ações</th>
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
              <td>
                {c.status !== "CANCELADA" && c.status !== "CONCLUIDA" ? (
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => onCancelar(c)}
                  >
                    Cancelar
                  </button>
                ) : (
                  <span className="muted">—</span>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
