/* eslint-disable no-restricted-globals */
import React, { useEffect, useRef } from 'react';
import { useData } from '../../utils/Context';
import { EASE_OUT_CUBIC } from '../../constants';
import * as S from './style';
import Header from '../Header';
import Footer from '../Footer';
import PriceStaticBar from '../PriceStaticBar';
import InteriorColor from '../../pages/InteriorColorPage';
import TrimSelect from '../../pages/TrimSelectPage';
import ModelType from '../../pages/ModelTypePage';
import ExteriorColor from '../../pages/ExteriorColorPage';
import OptionPicker from '../../pages/OptionPickerPage';
import Estimation from '../../pages/EstimationPage';

function Interaction() {
  const { setTrimState, page } = useData();
  const pageRef = useRef();

  const prevPage = () =>
    setTrimState((prevState) => ({ ...prevState, page: history.state.nowPage }));

  useEffect(() => {
    window.addEventListener('popstate', prevPage);
    return () => {
      window.removeEventListener('popstate', prevPage);
    };
  }, []);

  useEffect(() => {
    if (history.state.nowPage !== page) {
      history.pushState({ nowPage: page }, '');
    }
  }, [page]);

  useEffect(() => {
    pageRef.current.style.transition = `all 0.5s ${EASE_OUT_CUBIC}`;
    pageRef.current.style.transform = `translateX(min(-${page - 1}00%, -${(page - 1) * 1280}px))`;
  }, [page]);

  return (
    <S.Interaction>
      <Header />
      <S.Page ref={pageRef}>
        <TrimSelect />
        <ModelType />
        <ExteriorColor />
        <InteriorColor />
        <OptionPicker />
        <Estimation />
      </S.Page>
      <Footer />
      <PriceStaticBar />
    </S.Interaction>
  );
}

export default Interaction;
