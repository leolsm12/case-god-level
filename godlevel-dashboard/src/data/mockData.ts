export interface Establishment {
  id: number;
  name: string;
  revenue: number;
  sales: number;
}

export interface Sale {
  date: string;
  value: number;
}

export const establishments: Establishment[] = [
  { id: 1, name: "Estabelecimento 1", revenue: 5000, sales: 150 },
  { id: 2, name: "Estabelecimento 2", revenue: 7000, sales: 200 },
  { id: 3, name: "Estabelecimento 3", revenue: 5000, sales: 150 },
];

export const salesData: Sale[] = [
  { date: "2025-10-01", value: 300 },
  { date: "2025-10-02", value: 350 },
];