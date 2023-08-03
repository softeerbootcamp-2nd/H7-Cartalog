import React, { useState, useEffect, useRef } from 'react';
import * as S from './InteractionStyle';

import Header from '../components/Header/Header';
import Home from '../pages/Home/Home';

function Interaction() {
  const [page, setPage] = useState(0);
  const pageRef = useRef();
  const nextPage = () => setPage(page + 1);

  // useEffect(() => {
  //   pageRef.current.style.transition = 'all 0.5s ease-in-out';
  //   pageRef.current.style.transform = `translateX(-${page}00%)`;
  //   console.log('눌럿니');
  // }, [page]);

  return (
    <S.Interaction>
      <Header />
      <S.Page onClick={nextPage} ref={pageRef}>
        <Home />
      </S.Page>
    </S.Interaction>
  );
}

export default Interaction;
