import React, { useState } from 'react';
import ChartCard from '../../components/ChartCard';
import './Details.css';

/**
 * EXPLAINER:
 * - usamos dados estáticos `storesData` (você substituirá pelo backend depois).
 * - cada store tem séries para revenue/sales/orders (same length).
 * - o sidebar permite configurar até 3 gráficos; para cada gráfico escolhe-se tipo e stores (multi).
 */

/* MOCK: estabelecimentos com séries (exemplo) */
const storesData = [
  {
    id: 1,
    name: 'Loja A',
    revenue: [1200, 1500, 1700, 1400, 1800, 2000],
    sales: [40, 50, 55, 48, 60, 70],
    orders: [10, 12, 14, 11, 13, 16],
  },
  {
    id: 2,
    name: 'Loja B',
    revenue: [900, 1100, 1000, 1200, 1250, 1300],
    sales: [30, 35, 33, 38, 40, 42],
    orders: [8, 9, 7, 10, 11, 12],
  },
  {
    id: 3,
    name: 'Loja C',
    revenue: [2000, 2100, 1900, 2200, 2300, 2400],
    sales: [70, 75, 68, 80, 82, 90],
    orders: [20, 22, 19, 23, 24, 25],
  },
];

const timeLabels = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun'];

type ChartType = 'bar' | 'line' | 'pie';
type DataType = 'revenue' | 'sales' | 'orders';

interface PerChartConfig {
  chartType: ChartType;
  selectedStoreIds: number[];
  dataType: DataType;
}

const Details: React.FC = () => {
  const [numCharts, setNumCharts] = useState<number>(2);

  const [chartsConfig, setChartsConfig] = useState<PerChartConfig[]>(
    Array.from({ length: 3 }).map((_, i) => ({
      chartType: i === 0 ? 'bar' : i === 1 ? 'line' : 'pie',
      selectedStoreIds: i === 0 ? [1, 2] : [1],
      dataType: i === 0 ? 'sales' : i === 1 ? 'revenue' : 'orders',
    }))
  );

  const toggleStoreForChart = (chartIndex: number, storeId: number) => {
    setChartsConfig((prev) =>
      prev.map((cfg, idx) =>
        idx !== chartIndex
          ? cfg
          : {
              ...cfg,
              selectedStoreIds: cfg.selectedStoreIds.includes(storeId)
                ? cfg.selectedStoreIds.filter((s) => s !== storeId)
                : [...cfg.selectedStoreIds, storeId],
            }
      )
    );
  };

  const setChartType = (chartIndex: number, type: ChartType) => {
    setChartsConfig((prev) =>
      prev.map((cfg, idx) => (idx === chartIndex ? { ...cfg, chartType: type } : cfg))
    );
  };

  const setDataType = (chartIndex: number, type: DataType) => {
    setChartsConfig((prev) =>
      prev.map((cfg, idx) => (idx === chartIndex ? { ...cfg, dataType: type } : cfg))
    );
  };

  const buildDatasetsForChart = (cfg: PerChartConfig) => {
    if (cfg.chartType === 'pie') {
      const data = cfg.selectedStoreIds.map((id) => {
        const store = storesData.find((s) => s.id === id)!;
        return store[cfg.dataType][store[cfg.dataType].length - 1];
      });
      const labels = cfg.selectedStoreIds.map((id) => storesData.find((s) => s.id === id)!.name);
      const background = ['#FFCE56', '#FF6384', '#36A2EB', '#9966FF', '#4BC0C0'];
      return {
        labels,
        datasets: [
          {
            label: 'Comparativo',
            data,
            backgroundColor: background.slice(0, data.length),
          },
        ],
      };
    }

    const datasets = cfg.selectedStoreIds.map((id, idx) => {
      const store = storesData.find((s) => s.id === id)!;
      const colors = ['#FF6384', '#36A2EB', '#FFCE56', '#9966FF', '#4BC0C0'];
      return {
        label: store.name,
        data: store[cfg.dataType],
        backgroundColor: colors[idx % colors.length],
        borderColor: colors[idx % colors.length],
      };
    });

    return { labels: timeLabels, datasets };
  };

  return (
    <div className="details-container">
      <aside className="sidebar">
        <h3>⚙️ Configurações</h3>

        <div className="field">
          <label>🔢 Quantidade de gráficos</label>
          <input
            type="number"
            min={1}
            max={3}
            value={numCharts}
            onChange={(e) =>
              setNumCharts(Math.max(1, Math.min(3, Number(e.target.value))))
            }
          />
        </div>

        <div className="divider" />

        {Array.from({ length: numCharts }).map((_, chartIndex) => {
          const cfg = chartsConfig[chartIndex];
          return (
            <div className="chart-config" key={chartIndex}>
              <h4>Gráfico {chartIndex + 1}</h4>

              <label>Tipo de gráfico</label>
              <div className="btn-row">
                {(['bar', 'line', 'pie'] as ChartType[]).map((t) => (
                  <button
                    key={t}
                    className={`small-btn ${cfg.chartType === t ? 'active' : ''}`}
                    onClick={() => setChartType(chartIndex, t)}
                  >
                    {t.toUpperCase()}
                  </button>
                ))}
              </div>

              <label style={{ marginTop: 8 }}>Tipo de dado</label>
              <select
                value={cfg.dataType}
                onChange={(e) => setDataType(chartIndex, e.target.value as DataType)}
              >
                <option value="revenue">Faturamento</option>
                <option value="sales">Vendas</option>
                <option value="orders">Pedidos</option>
              </select>

              <label style={{ marginTop: 8 }}>Comparar estabelecimentos</label>
              <div className="stores-list">
                {storesData.map((s) => (
                  <label key={s.id} className="checkbox-label">
                    <input
                      type="checkbox"
                      checked={cfg.selectedStoreIds.includes(s.id)}
                      onChange={() => toggleStoreForChart(chartIndex, s.id)}
                    />
                    {s.name}
                  </label>
                ))}
              </div>
            </div>
          );
        })}
      </aside>

      <main className="details-content">
        <h2>📈 Painel Comparativo</h2>
        <div className="charts-area">
          {Array.from({ length: numCharts }).map((_, i) => {
            const cfg = chartsConfig[i];
            const built = buildDatasetsForChart(cfg);
            return (
              <ChartCard
                key={i}
                type={cfg.chartType}
                title={`${cfg.dataType.toUpperCase()} (${cfg.chartType})`}
                labels={built.labels}
                datasets={built.datasets as any}
              />
            );
          })}
        </div>
      </main>
    </div>
  );
};

export default Details;