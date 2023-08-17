import { useEffect, useState } from 'react';
import { useData } from '../../utils/Context';
import * as S from './style';
import NextButton from '../NextButton';
import HMGData from '../../pages/ModelTypePage/Pick/HMGData';

function Footer() {
  const { page } = useData();
  const [isVisible, setIsVisible] = useState(false);
  const [isHMGData, setIsHMGData] = useState(false);

  useEffect(() => {
    setIsVisible(page !== 1);
    setIsHMGData(page === 1 || page === 2);
  }, [page]);

  return (
    <S.Footer className={isVisible ? 'visible' : 'hidden'}>
      <S.FooterEnd>
        <S.HMGDataFade className={isHMGData ? 'visible' : 'hidden'}>
          <HMGData />
        </S.HMGDataFade>
        <NextButton />
      </S.FooterEnd>
    </S.Footer>
  );
}

export default Footer;
