import React from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  ArcElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Bar, Line, Pie } from 'react-chartjs-2';
import './ChartCard.css';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  ArcElement,
  Title,
  Tooltip,
  Legend
);

type ChartType = 'bar' | 'line' | 'pie';

interface Dataset {
  label: string;
  data: number[];
  backgroundColor?: string | string[];
  borderColor?: string;
}

interface ChartCardProps {
  type: ChartType;
  title?: string;
  labels: string[];
  datasets: Dataset[]; // for bar/line: multiple datasets; for pie: datasets array must contain one dataset with data + backgroundColor array
}

const ChartCard: React.FC<ChartCardProps> = ({ type, title, labels, datasets }) => {
  const options = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { position: 'top' as const },
      title: { display: !!title, text: title },
      tooltip: { mode: 'index' as const, intersect: false },
    },
    interaction: { mode: 'nearest' as const, intersect: false },
  };

  const data =
    type === 'pie'
      ? {
          labels,
          datasets: datasets.length > 0 ? [{ data: datasets[0].data, backgroundColor: datasets[0].backgroundColor }] : [],
        }
      : {
          labels,
          datasets: datasets.map((d) => ({
            label: d.label,
            data: d.data,
            backgroundColor: d.backgroundColor,
            borderColor: d.borderColor || d.backgroundColor,
            fill: false,
          })),
        };

  return (
    <div className="chart-card">
      <div className="chart-card-body">
        <div className="chart-wrapper">
          {type === 'bar' && <Bar data={data as any} options={options} />}
          {type === 'line' && <Line data={data as any} options={options} />}
          {type === 'pie' && <Pie data={data as any} options={options} />}
        </div>
      </div>
    </div>
  );
};

export default ChartCard;