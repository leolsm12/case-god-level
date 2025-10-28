import React, { useState } from 'react';
import TopMenu from './components/TopMenu';
import Home from './pages/Home';
import Details from './pages/Details';

const App: React.FC = () => {
  const [page, setPage] = useState('home');

  // Função que o TopMenu vai chamar ao clicar nos botões
  const handleNavigate = (targetPage: string) => {
    setPage(targetPage);
  };

  return (
    <div>
      {/* Passamos o handleNavigate corretamente */}
      <TopMenu currentPage={page} onNavigate={handleNavigate} />

      {/* Renderiza a página conforme o estado */}
      {page === 'home' && <Home />}
      {page === 'details' && <Details/>}
    </div>
  );
};

export default App;