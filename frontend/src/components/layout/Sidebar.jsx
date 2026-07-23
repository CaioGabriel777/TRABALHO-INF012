import { NavLink } from "react-router-dom";

const links = [
  { to: "/dashboard",    label: "Dashboard" },
  { to: "/produtos",     label: "Produtos" },
  { to: "/categorias",   label: "Categorias" },
  { to: "/fornecedores", label: "Fornecedores" },
  { to: "/clientes",     label: "Clientes" },
  { to: "/compras",      label: "Compras" },
];

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="brand">INF012 · Gestão</div>
      <nav>
        {links.map((l) => (
          <NavLink key={l.to} to={l.to}>
            {l.label}
          </NavLink>
        ))}
      </nav>
    </aside>
  );
}
