import React, { useState, useEffect, useRef } from 'react';
import * as S from './style';
import Header from '../components/Header';
import TrimSelectPage from '../pages/TrimSelectPage';
import ModelTypePage from '../pages/ModelTypePage';
import ExteriorColorPage from '../pages/ExteriorColorPage';
import InteriorColorPage from '../pages/InteriorColorPage';
import AddOptionPage from '../pages/AddOptionPage';
import EstimateFinishPage from '../pages/EstimateFinishPage';

function Interaction() {
  const [page, setPage] = useState(0);
  const pageRef = useRef();
  const nextPage = () => setPage(page + 1);
  const prevPage = () => setPage(history.state.nowPage);

  useEffect(() => window.addEventListener('popstate', prevPage), []);

  useEffect(() => {
    pageRef.current.style.transition = 'all 0.7s ease-in-out';
    pageRef.current.style.transform = `translateX(-${page}00%)`;
    if (history.state.nowPage !== page) history.pushState({ nowPage: page }, '');
  }, [page]);

  return (
    <S.Interaction>
      <Header />
      <S.Page ref={pageRef}>
        <TrimSelectPage nextPage={nextPage} />
        <ModelTypePage nextPage={nextPage} />
        <ExteriorColorPage nextPage={nextPage} />
        <InteriorColorPage nextPage={nextPage} />
        <AddOptionPage nextPage={nextPage} />
        <EstimateFinishPage />
      </S.Page>
    </S.Interaction>
  );
}

export default Interaction;
