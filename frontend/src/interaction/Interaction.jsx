import React, { useState, useEffect, useRef } from 'react';
import * as S from './InteractionStyle';

import Header from '../components/Header/Header';
import Home from '../pages/TrimSelect/TrimSelect';

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
        <Home nextPage={nextPage} />
        <Home nextPage={nextPage} />
        <Home nextPage={nextPage} />
        <Home nextPage={nextPage} />
        <Home nextPage={nextPage} />
        <Home nextPage={nextPage} />
      </S.Page>
    </S.Interaction>
  );
}

export default Interaction;
