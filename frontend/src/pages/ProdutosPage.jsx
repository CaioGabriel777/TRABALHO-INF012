import { useEffect, useState } from "react";
import { useProdutos } from "../hooks/useProdutos";
import {
  criarProduto,
  atualizarProduto,
  removerProduto,
} from "../api/produtoApi";
import { listarCategorias } from "../api/categoriaApi";
import { listarFornecedores } from "../api/fornecedorApi";
import ProdutoList from "../components/produto/ProdutoList";
import ProdutoForm from "../components/produto/ProdutoForm";

export default function ProdutosPage() {
  const { produtos, carregando, erro, recarregar } = useProdutos();
  const [categorias, setCategorias] = useState([]);
  const [fornecedores, setFornecedores] = useState([]);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [editando, setEditando] = useState(null);

  useEffect(() => {
    listarCategorias().then(setCategorias).catch(() => {});
    listarFornecedores().then(setFornecedores).catch(() => {});
  }, []);

  const novo = () => {
    setEditando(null);
    setMostrarForm(true);
  };

  const editar = (produto) => {
    // ProdutoResponseDto -> formato do formulario
    setEditando({
      id: produto.id,
      nome: produto.nome,
      descricao: produto.descricao,
      preco: produto.preco,
      quantidadeEstoque: produto.quantidadeEstoque,
      estoqueMinimo: produto.estoqueMinimo ?? 0,
      categoriaId:
        categorias.find((c) => c.nome === produto.categoriaNome)?.id || "",
      fornecedorId: produto.fornecedor?.id || "",
    });
    setMostrarForm(true);
  };

  const salvar = async (dados) => {
    if (editando?.id) {
      await atualizarProduto(editando.id, dados);
    } else {
      await criarProduto(dados);
    }
    setMostrarForm(false);
    setEditando(null);
    recarregar();
  };

  const remover = async (produto) => {
    if (!window.confirm(`Remover o produto "${produto.nome}"?`)) return;
    await removerProduto(produto.id);
    recarregar();
  };

  return (
    <div>
      <div className="page-head">
        <h1>Produtos</h1>
        {!mostrarForm && (
          <button className="btn" onClick={novo}>
            + Novo produto
          </button>
        )}
      </div>

      {mostrarForm ? (
        <ProdutoForm
          inicial={editando}
          categorias={categorias}
          fornecedores={fornecedores}
          onSalvar={salvar}
          onCancelar={() => {
            setMostrarForm(false);
            setEditando(null);
          }}
        />
      ) : (
        <>
          {erro && <div className="alert alert-error">{erro}</div>}
          {carregando ? (
            <div className="loading">Carregando...</div>
          ) : (
            <ProdutoList
              produtos={produtos}
              onEditar={editar}
              onRemover={remover}
            />
          )}
        </>
      )}
    </div>
  );
}
