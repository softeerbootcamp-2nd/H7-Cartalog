import React, { Suspense, useEffect, useRef } from 'react';
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
import Skeleton from '../Skeleton';
import { TRIM_SELECT } from '../../pages/TrimSelectPage/constants';
import { MODEL_TYPE } from '../../pages/ModelTypePage/constants';
import { EXTERIOR_COLOR } from '../../pages/ExteriorColorPage/constants';
import { INTERIOR_COLOR } from '../../pages/InteriorColorPage/constants';

function Interaction() {
  const data = useData();
  const pageRef = useRef();
  const isFetchMap = {
    1: data.trim.isFetch,
    2: data.modelType.isFetch,
    3: data.exteriorColor.isFetch,
    4: data.interiorColor.isFetch,
    5: data.optionPicker.isFetch,
    6: data.estimation.isFetch,
  };

  useEffect(() => {
    const handlePopstate = () => {
      const canGoBack = isFetchMap[window.history.state.nowPage] || false;

      if (!canGoBack) {
        const { nowPage } = window.history.state;
        if (nowPage !== data.page) {
          window.history.replaceState({ nowPage: data.page }, '');
          return;
        }
      }
      data.setTrimState((prevState) => ({ ...prevState, page: window.history.state.nowPage }));
    };

    window.addEventListener('popstate', handlePopstate);

    return () => {
      window.removeEventListener('popstate', handlePopstate);
    };
  }, [data]);

  useEffect(() => {
    pageRef.current.style.transition = `transform 0.5s ${EASE_OUT_CUBIC}`;
    pageRef.current.style.transform = `translateX(min(-${data.page - 1}00%, -${
      (data.page - 1) * 1280
    }px))`;

    if (window.history.state.nowPage !== data.page) {
      window.history.pushState({ nowPage: data.page }, '');
    }
  }, [data.page]);

  const pageToRender = (page) => isFetchMap[page];

  return (
    <S.Interaction>
      <Header />
      <S.Page ref={pageRef}>
        <Suspense fallback={<Skeleton type={TRIM_SELECT.TYPE} />}>
          <TrimSelect />
        </Suspense>
        {isFetchMap[1] && (
          <Suspense fallback={<Skeleton type={MODEL_TYPE.TYPE} />}>
            <ModelType />
          </Suspense>
        )}
        {isFetchMap[2] && (
          <Suspense fallback={<Skeleton type={EXTERIOR_COLOR.TYPE} />}>
            <ExteriorColor />
          </Suspense>
        )}
        {isFetchMap[3] && (
          <Suspense fallback={<Skeleton type={INTERIOR_COLOR.TYPE} />}>
            <InteriorColor />
          </Suspense>
        )}
        {isFetchMap[4] && <OptionPicker />}
        {isFetchMap[5] && <Estimation />}
      </S.Page>
      <Footer />
      <PriceStaticBar />
    </S.Interaction>
  );
}

export default Interaction;
