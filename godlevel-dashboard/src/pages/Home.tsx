import React from 'react';
import './Home.css';
import { establishments } from '../data/mockData';
import LocalCard from '../components/LocalCard';

const Home: React.FC = () => {
  const totalRevenue = establishments.reduce((acc, l) => acc + l.revenue, 0);
  const totalSales = establishments.reduce((acc, l) => acc + l.sales, 0);

  return (
    <div className="home-container">
      <div className="card-scroll">
        {establishments.map((local) => (
          <LocalCard key={local.id} local={local} />
        ))}
      </div>

      <div className="summary-box">
        <div>
          <h3>Faturamento Total</h3>
          <p>R${totalRevenue}</p>
        </div>
        <div>
          <h3>Vendas Totais</h3>
          <p>{totalSales}</p>
        </div>
        <div>
          <h3>Média por Estabelecimento</h3>
          <p>R${Math.round(totalRevenue / establishments.length)}</p>
        </div>
      </div>
    </div>
  );
};

export default Home;