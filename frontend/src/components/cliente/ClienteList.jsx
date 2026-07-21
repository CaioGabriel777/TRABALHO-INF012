export default function ClienteList({ clientes, onEditar, onRemover }) {
  if (clientes.length === 0) {
    return <div className="empty">Nenhum cliente cadastrado.</div>;
  }

  return (
    <div className="card">
      <table>
        <thead>
          <tr>
            <th>Nome</th>
            <th>CPF</th>
            <th>E-mail</th>
            <th>Telefone</th>
            <th style={{ width: 160 }}>Ações</th>
          </tr>
        </thead>
        <tbody>
          {clientes.map((c) => (
            <tr key={c.id}>
              <td>{c.nome}</td>
              <td>{c.cpf}</td>
              <td>{c.email}</td>
              <td>{c.telefone || "—"}</td>
              <td>
                <div className="row-actions">
                  <button
                    className="btn btn-outline btn-sm"
                    onClick={() => onEditar(c)}
                  >
                    Editar
                  </button>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => onRemover(c)}
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
  );
}
