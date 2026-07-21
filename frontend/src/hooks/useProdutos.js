import { useCallback, useEffect, useState } from "react";
import { listarProdutos } from "../api/produtoApi";

// Hook simples para carregar e recarregar a lista de produtos
export function useProdutos() {
  const [produtos, setProdutos] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState(null);

  const carregar = useCallback(async () => {
    setCarregando(true);
    setErro(null);
    try {
      setProdutos(await listarProdutos());
    } catch (e) {
      setErro(e.message);
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    carregar();
  }, [carregar]);

  return { produtos, carregando, erro, recarregar: carregar };
}
