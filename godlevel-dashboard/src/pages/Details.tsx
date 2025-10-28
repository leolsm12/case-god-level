import React, { useState } from 'react';
import Sidebar from '../components/Sidebar';
import './Details.css';

const Details: React.FC = () => {
  const [selectedChart, setSelectedChart] = useState('Pizza');
  const [selectedData, setSelectedData] = useState<string[]>(['Faturamento']);
  const [numCharts, setNumCharts] = useState(1);

  return (
    <div className="details-container">
      <Sidebar
        selectedChart={selectedChart}
        setSelectedChart={setSelectedChart}
        selectedData={selectedData}
        setSelectedData={setSelectedData}
        numCharts={numCharts}
        setNumCharts={setNumCharts}
      />

      <main className="details-content">
        <h1>Visualização de Dados</h1>
        <p>Tipo de gráfico: {selectedChart}</p>
        <p>Dados selecionados: {selectedData.join(', ')}</p>
        <p>Quantidade de gráficos: {numCharts}</p>

        <div className="charts-area">
          {Array.from({ length: numCharts }).map((_, i) => (
            <div key={i} className="chart-box">
              <p>📊 Gráfico {i + 1} - {selectedChart}</p>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
};

export default Details;