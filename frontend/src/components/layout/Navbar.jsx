import { useLocation } from "react-router-dom";

const titulos = {
  "/produtos": "Produtos",
  "/categorias": "Categorias",
  "/fornecedores": "Fornecedores",
  "/clientes": "Clientes",
  "/compras": "Compras",
};

export default function Navbar() {
  const { pathname } = useLocation();
  const chave = Object.keys(titulos).find((k) => pathname.startsWith(k));
  return <header className="navbar">{titulos[chave] || "Gestão"}</header>;
}
