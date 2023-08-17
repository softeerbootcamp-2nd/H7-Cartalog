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
    setIsVisible(!!page && page !== 1);
    setIsHMGData([1, 2].includes(page));
  }, [page]);

  return (
    <S.Footer className={isVisible ? 'visible' : ''}>
      <S.FooterEnd>
        <S.HMGDataFade className={isHMGData ? 'visible' : ''}>
          <HMGData />
        </S.HMGDataFade>
        <NextButton />
      </S.FooterEnd>
    </S.Footer>
  );
}

export default Footer;
