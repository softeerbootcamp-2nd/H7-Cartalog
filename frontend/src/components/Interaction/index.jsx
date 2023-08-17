import React, { useEffect, useRef } from 'react';
import { useData } from '../../utils/Context';
import * as S from './style';
import Header from '../Header';
import Footer from '../Footer';
import InteriorColor from '../../pages/InteriorColorPage';
import TrimSelect from '../../pages/TrimSelectPage';
import ModelType from '../../pages/ModelTypePage';
import ExteriorColor from '../../pages/ExteriorColorPage';
import OptionPicker from '../../pages/OptionPickerPage';
import Estimation from '../../pages/EstimationPage';

function Interaction() {
  const { page } = useData();
  const pageRef = useRef();

  useEffect(() => {
    pageRef.current.style.transition = 'all 0.7s ease-in-out';
    pageRef.current.style.transform = `translateX(-${page - 1}00%)`;
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
    </S.Interaction>
  );
}

export default Interaction;
