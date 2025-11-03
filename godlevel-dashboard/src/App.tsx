import React, { useState } from 'react';
import TopMenu from './components/TopMenu';
import Home from './pages/Home/Home';
import Details from './pages/Details/Details';
import Login from './pages/Login/Login'; // ajuste a letra maiúscula

const App: React.FC = () => {
  const [page, setPage] = useState('login');

  // Função que o TopMenu e o Login vão chamar para navegar
  const handleNavigate = (targetPage: string) => {
    setPage(targetPage);
  };

  return (
    <div>
      {/* Mostra TopMenu apenas se não estiver na tela de login */}
      {page !== 'login' && <TopMenu currentPage={page} onNavigate={handleNavigate} />}

      {/* Renderiza a página conforme o estado */}
      {page === 'login' && <Login onLogin={() => handleNavigate('home')} />}
      {page === 'home' && <Home />}
      {page === 'details' && <Details />}
    </div>
  );
};

export default App;