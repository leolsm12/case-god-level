import React from 'react';
import './Sidebar.css';

interface SidebarProps {
  selectedChart: string;
  setSelectedChart: (type: string) => void;
  selectedData: string[];
  setSelectedData: React.Dispatch<React.SetStateAction<string[]>>;
  numCharts: number;
  setNumCharts: (num: number) => void;
}

const Sidebar: React.FC<SidebarProps> = ({
  selectedChart,
  setSelectedChart,
  selectedData,
  setSelectedData,
  numCharts,
  setNumCharts
}) => {
  const chartTypes = ['Pizza', 'Linha', 'Barra'];
  const dataOptions = ['Faturamento', 'Vendas', 'Ticket Médio'];

  const toggleData = (option: string) => {
    setSelectedData(prev =>
      prev.includes(option) ? prev.filter(d => d !== option) : [...prev, option]
    );
  };

  return (
    <aside className="sidebar">
      <h2>Configurações</h2>

      <div className="sidebar-section">
        <h3>Tipo de Gráfico</h3>
        {chartTypes.map(type => (
          <button
            key={type}
            className={`sidebar-btn ${selectedChart === type ? 'active' : ''}`}
            onClick={() => setSelectedChart(type)}
          >
            {type}
          </button>
        ))}
      </div>

      <div className="sidebar-section">
        <h3>Dados a Exibir</h3>
        {dataOptions.map(option => (
          <label key={option} className="checkbox-label">
            <input
              type="checkbox"
              checked={selectedData.includes(option)}
              onChange={() => toggleData(option)}
            />
            {option}
          </label>
        ))}
      </div>

      <div className="sidebar-section">
        <h3>Quantidade de Gráficos</h3>
        <input
          type="number"
          min={1}
          max={3}
          value={numCharts}
          onChange={e => setNumCharts(Number(e.target.value))}
        />
      </div>
    </aside>
  );
};

export default Sidebar;