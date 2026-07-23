import { useEffect, useState, useCallback } from "react";
import {
  AreaChart, Area,
  BarChart, Bar,
  PieChart, Pie, Cell,
  XAxis, YAxis, CartesianGrid, Tooltip, Legend,
  ResponsiveContainer,
} from "recharts";
import { listarCompras } from "../api/compraApi";
import { listarClientes } from "../api/clienteApi";
import { listarProdutos } from "../api/produtoApi";

const moeda = (v) =>
  Number(v).toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

const MESES = ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"];

const STATUS_CORES = {
  CONCLUIDA:  "#2f5d50",
  CONFIRMADA: "#5ba48f",
  PENDENTE:   "#c9933e",
  CANCELADA:  "#a3423a",
};

function kpiUltimos6Meses(compras) {
  const hoje = new Date();
  const meses = [];
  for (let i = 5; i >= 0; i--) {
    const d = new Date(hoje.getFullYear(), hoje.getMonth() - i, 1);
    meses.push({ label: MESES[d.getMonth()], ano: d.getFullYear(), mes: d.getMonth() });
  }

  return meses.map(({ label, ano, mes }) => {
    const doMes = compras.filter((c) => {
      const d = new Date(c.dataCriacao);
      return d.getFullYear() === ano && d.getMonth() === mes;
    });
    const receita = doMes
      .filter((c) => c.status === "CONCLUIDA")
      .reduce((s, c) => s + Number(c.valorTotal), 0);
    return { mes: label, compras: doMes.length, receita };
  });
}

function topProdutos(compras) {
  const contagem = {};
  compras.forEach((c) => {
    (c.itens || []).forEach((item) => {
      const key = `Produto #${item.produtoId}`;
      contagem[key] = (contagem[key] || 0) + item.quantidade;
    });
  });
  return Object.entries(contagem)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 5)
    .map(([nome, qtd]) => ({ nome, qtd }));
}

function distribuicaoStatus(compras) {
  const mapa = {};
  compras.forEach((c) => {
    mapa[c.status] = (mapa[c.status] || 0) + 1;
  });
  return Object.entries(mapa).map(([name, value]) => ({ name, value }));
}

const CustomTooltipMoeda = ({ active, payload, label }) => {
  if (!active || !payload?.length) return null;
  return (
    <div className="dash-tooltip">
      <div className="dash-tooltip-label">{label}</div>
      {payload.map((p) => (
        <div key={p.dataKey} style={{ color: p.color }}>
          {p.name}: {p.dataKey === "receita" ? moeda(p.value) : p.value}
        </div>
      ))}
    </div>
  );
};

export default function DashboardPage() {
  const [compras, setCompras]   = useState([]);
  const [clientes, setClientes] = useState([]);
  const [produtos, setProdutos] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro]         = useState(null);

  const carregar = useCallback(async () => {
    setCarregando(true);
    setErro(null);
    try {
      const [co, cl, pr] = await Promise.all([
        listarCompras(),
        listarClientes(),
        listarProdutos(),
      ]);
      setCompras(co);
      setClientes(cl);
      setProdutos(pr);
    } catch (e) {
      setErro(e.message);
    } finally {
      setCarregando(false);
    }
  }, []);

  useEffect(() => { carregar(); }, [carregar]);

  if (carregando) return <div className="loading">Carregando dashboard...</div>;
  if (erro)       return <div className="alert alert-error">{erro}</div>;

  const receitaTotal    = compras.filter((c) => c.status === "CONCLUIDA").reduce((s, c) => s + Number(c.valorTotal), 0);
  const dadosMensais    = kpiUltimos6Meses(compras);
  const dadosTop        = topProdutos(compras);
  const dadosStatus     = distribuicaoStatus(compras);

  return (
    <div>
      <div className="page-head">
        <h1>Dashboard</h1>
      </div>

      {/* KPI Cards */}
      <div className="dash-kpi-grid">
        <div className="dash-kpi-card">
          <div className="dash-kpi-label">Total de Compras</div>
          <div className="dash-kpi-value">{compras.length}</div>
        </div>
        <div className="dash-kpi-card dash-kpi-card--accent">
          <div className="dash-kpi-label">Receita (Concluídas)</div>
          <div className="dash-kpi-value">{moeda(receitaTotal)}</div>
        </div>
        <div className="dash-kpi-card">
          <div className="dash-kpi-label">Clientes Cadastrados</div>
          <div className="dash-kpi-value">{clientes.length}</div>
        </div>
        <div className="dash-kpi-card">
          <div className="dash-kpi-label">Produtos no Catálogo</div>
          <div className="dash-kpi-value">{produtos.length}</div>
        </div>
      </div>

      {/* Linha 1: Area chart + Pie chart */}
      <div className="dash-row">
        <div className="dash-chart-card" style={{ flex: 2 }}>
          <h3 className="dash-chart-title">Receita por Mês (últimos 6 meses)</h3>
          <ResponsiveContainer width="100%" height={240}>
            <AreaChart data={dadosMensais} margin={{ top: 8, right: 16, left: 8, bottom: 0 }}>
              <defs>
                <linearGradient id="gradReceita" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%"  stopColor="#2f5d50" stopOpacity={0.25} />
                  <stop offset="95%" stopColor="#2f5d50" stopOpacity={0}    />
                </linearGradient>
              </defs>
              <CartesianGrid strokeDasharray="3 3" stroke="#e8e8e4" />
              <XAxis dataKey="mes"    tick={{ fontSize: 12 }} />
              <YAxis tickFormatter={(v) => `R$${(v / 1000).toFixed(0)}k`} tick={{ fontSize: 11 }} />
              <Tooltip content={<CustomTooltipMoeda />} />
              <Area
                type="monotone" dataKey="receita" name="Receita"
                stroke="#2f5d50" strokeWidth={2}
                fill="url(#gradReceita)"
              />
            </AreaChart>
          </ResponsiveContainer>
        </div>

        <div className="dash-chart-card" style={{ flex: 1 }}>
          <h3 className="dash-chart-title">Compras por Status</h3>
          <ResponsiveContainer width="100%" height={240}>
            <PieChart>
              <Pie
                data={dadosStatus}
                cx="50%"
                cy="50%"
                innerRadius={55}
                outerRadius={90}
                paddingAngle={3}
                dataKey="value"
              >
                {dadosStatus.map((entry) => (
                  <Cell key={entry.name} fill={STATUS_CORES[entry.name] || "#aaa"} />
                ))}
              </Pie>
              <Tooltip formatter={(v, n) => [v, n]} />
              <Legend iconType="circle" iconSize={10} />
            </PieChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Linha 2: Bar volumes + Bar top produtos */}
      <div className="dash-row">
        <div className="dash-chart-card" style={{ flex: 1 }}>
          <h3 className="dash-chart-title">Volume de Compras por Mês</h3>
          <ResponsiveContainer width="100%" height={220}>
            <BarChart data={dadosMensais} margin={{ top: 8, right: 16, left: 0, bottom: 0 }}>
              <CartesianGrid strokeDasharray="3 3" stroke="#e8e8e4" vertical={false} />
              <XAxis dataKey="mes" tick={{ fontSize: 12 }} />
              <YAxis allowDecimals={false} tick={{ fontSize: 11 }} />
              <Tooltip content={<CustomTooltipMoeda />} />
              <Bar dataKey="compras" name="Pedidos" fill="#5ba48f" radius={[3, 3, 0, 0]} />
            </BarChart>
          </ResponsiveContainer>
        </div>

        <div className="dash-chart-card" style={{ flex: 1 }}>
          <h3 className="dash-chart-title">Top 5 Produtos Mais Pedidos</h3>
          {dadosTop.length === 0 ? (
            <div className="empty">Sem itens para exibir.</div>
          ) : (
            <ResponsiveContainer width="100%" height={220}>
              <BarChart
                layout="vertical"
                data={dadosTop}
                margin={{ top: 8, right: 16, left: 80, bottom: 0 }}
              >
                <CartesianGrid strokeDasharray="3 3" stroke="#e8e8e4" horizontal={false} />
                <XAxis type="number" allowDecimals={false} tick={{ fontSize: 11 }} />
                <YAxis type="category" dataKey="nome" tick={{ fontSize: 12 }} width={80} />
                <Tooltip />
                <Bar dataKey="qtd" name="Qtd. vendida" fill="#2f5d50" radius={[0, 3, 3, 0]} />
              </BarChart>
            </ResponsiveContainer>
          )}
        </div>
      </div>
    </div>
  );
}
