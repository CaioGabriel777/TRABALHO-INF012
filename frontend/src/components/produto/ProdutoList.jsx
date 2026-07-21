import ProdutoCard from "./ProdutoCard";

export default function ProdutoList({ produtos, onEditar, onRemover }) {
  if (produtos.length === 0) {
    return <div className="empty">Nenhum produto cadastrado.</div>;
  }

  return (
    <div className="grid">
      {produtos.map((p) => (
        <ProdutoCard
          key={p.id}
          produto={p}
          onEditar={onEditar}
          onRemover={onRemover}
        />
      ))}
    </div>
  );
}
