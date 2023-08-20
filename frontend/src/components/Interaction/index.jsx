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
  const data = useData();
  const pageRef = useRef();

  useEffect(() => {
    const handlePopstate = () => {
      const isFetchMap = {
        1: data.trim.isFetch,
        2: data.modelType.isFetch,
        3: data.exteriorColor.isFetch,
        4: data.interiorColor.isFetch,
        5: data.optionPicker.isFetch,
        6: data.estimation.isFetch,
      };

      const canGoBack = isFetchMap[history.state.nowPage] || false;

      if (!canGoBack) {
        const { nowPage } = history.state;
        if (nowPage !== data.page) {
          history.replaceState({ nowPage: data.page }, '');
          return;
        }
      }
      data.setTrimState((prevState) => ({ ...prevState, page: history.state.nowPage }));
    };

    window.addEventListener('popstate', handlePopstate);

    return () => {
      window.removeEventListener('popstate', handlePopstate);
    };
  }, [data]);

  useEffect(() => {
    pageRef.current.style.transition = `all 0.5s ${EASE_OUT_CUBIC}`;
    pageRef.current.style.transform = `translateX(min(-${data.page - 1}00%, -${
      (data.page - 1) * 1280
    }px))`;

    if (history.state.nowPage !== data.page) {
      history.pushState({ nowPage: data.page }, '');
    }
  }, [data.page]);

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
