import { useCallback, useEffect, useState } from "react";
import { listarClientes } from "../api/clienteApi";

// Hook simples para carregar e recarregar a lista de clientes
export function useClientes() {
  const [clientes, setClientes] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState(null);

  const carregar = useCallback(async () => {
    setCarregando(true);
    setErro(null);
    try {
      setClientes(await listarClientes());
    } catch (e) {
      setErro(e.message);
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => {
    carregar();
  }, [carregar]);

  return { clientes, carregando, erro, recarregar: carregar };
}
