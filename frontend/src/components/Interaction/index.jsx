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
import TrimSelectSkeleton from '../Skeleton/TrimSelect';
import ModelTypeSkeleton from '../Skeleton/ModelType';
import ExteriorColorSkeleton from '../Skeleton/ExteriorColor';
import InteriorColorSkeleton from '../Skeleton/InteriorColor';
import OptionPickerSkeleton from '../Skeleton/OptionPicker';
import EstimationSkeleton from '../Skeleton/Estimation';

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

  return (
    <S.Interaction>
      <Header />
      <S.Page ref={pageRef}>
        <Suspense fallback={<TrimSelectSkeleton />}>
          <TrimSelect />
        </Suspense>
        {isFetchMap[1] && (
          <Suspense fallback={<ModelTypeSkeleton />}>
            <ModelType />
          </Suspense>
        )}
        {isFetchMap[2] && (
          <Suspense fallback={<ExteriorColorSkeleton />}>
            <ExteriorColor />
          </Suspense>
        )}
        {isFetchMap[3] && (
          <Suspense fallback={<InteriorColorSkeleton />}>
            <InteriorColor />
          </Suspense>
        )}
        {isFetchMap[4] && (
          <Suspense fallback={<OptionPickerSkeleton />}>
            <OptionPicker />
          </Suspense>
        )}
        {isFetchMap[5] && (
          <Suspense fallback={<EstimationSkeleton />}>
            <Estimation />
          </Suspense>
        )}
      </S.Page>
      <Footer />
      <PriceStaticBar />
    </S.Interaction>
  );
}

export default Interaction;
