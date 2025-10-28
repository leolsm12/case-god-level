import React from 'react';
import { Establishment } from '../data/mockData';
import './LocalCard.css';

interface Props {
  local: Establishment;
}

const LocalCard: React.FC<Props> = ({ local }) => {
  return (
    <div className="local-card">
      <h3>{local.name}</h3>
      <p>Faturamento: R${local.revenue}</p>
      <p>Vendas: {local.sales}</p>
    </div>
  );
};

export default LocalCard;