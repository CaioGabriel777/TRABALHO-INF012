const moeda = (v) =>
  Number(v).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

export default function ProdutoCard({ produto, onEditar, onRemover }) {
  const semEstoque = produto.quantidadeEstoque <= 0;

  return (
    <div className="produto-card">
      <h3>{produto.nome}</h3>
      <div className="meta">{produto.categoriaNome}</div>
      <div className="price">{moeda(produto.preco)}</div>
      <div className="meta">
        Estoque: {produto.quantidadeEstoque}{" "}
        {semEstoque && <span className="badge off">esgotado</span>}
        {!produto.ativo && <span className="badge off">inativo</span>}
      </div>
      <div className="row-actions" style={{ marginTop: 12 }}>
        <button className="btn btn-outline btn-sm" onClick={() => onEditar(produto)}>
          Editar
        </button>
        <button className="btn btn-danger btn-sm" onClick={() => onRemover(produto)}>
          Remover
        </button>
      </div>
    </div>
  );
}
