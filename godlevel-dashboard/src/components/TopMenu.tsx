import React from 'react';
import './TopMenu.css';

interface TopMenuProps {
  onNavigate: (page: string) => void;
  currentPage: string;
}

const TopMenu: React.FC<TopMenuProps> = ({ onNavigate, currentPage }) => {
  return (
    <header className="topbar">
      <div className="logo">🍔 GodLevel</div>

      <nav className="nav-buttons">
        <button
          className={`nav-btn ${currentPage === 'home' ? 'active' : ''}`}
          onClick={() => onNavigate('home')}
        >
          🏠 Início
        </button>

        <button
          className={`nav-btn ${currentPage === 'details' ? 'active' : ''}`}
          onClick={() => onNavigate('details')}
        >
          📊 Detalhes
        </button>
      </nav>
    </header>
  );
};

export default TopMenu;