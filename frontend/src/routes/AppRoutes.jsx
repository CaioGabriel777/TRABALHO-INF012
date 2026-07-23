import { Routes, Route, Navigate } from "react-router-dom";
import DashboardPage from "../pages/DashboardPage";
import ProdutosPage from "../pages/ProdutosPage";
import CategoriasPage from "../pages/CategoriasPage";
import FornecedoresPage from "../pages/FornecedoresPage";
import ClientesPage from "../pages/ClientesPage";
import ComprasPage from "../pages/ComprasPage";

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/"            element={<Navigate to="/dashboard" replace />} />
      <Route path="/dashboard"   element={<DashboardPage />} />
      <Route path="/produtos"    element={<ProdutosPage />} />
      <Route path="/categorias"  element={<CategoriasPage />} />
      <Route path="/fornecedores" element={<FornecedoresPage />} />
      <Route path="/clientes"    element={<ClientesPage />} />
      <Route path="/compras"     element={<ComprasPage />} />
      <Route path="*"            element={<Navigate to="/dashboard" replace />} />
    </Routes>
  );
}
