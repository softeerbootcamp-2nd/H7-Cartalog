import React, { useEffect, useRef } from 'react';
import { useData } from '../../utils/Context';
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
import { EASE_OUT_CUBIC } from '../../constants';

function Interaction() {
  const { page, trim, price, budget } = useData();
  const pageRef = useRef();

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
      <PriceStaticBar
      // min={SelectModel?.minPrice}
      // max={SelectModel?.maxPrice}
      // price={TotalPrice(price)}
      // budget={budget}
      />
    </S.Interaction>
  );
}

export default Interaction;
