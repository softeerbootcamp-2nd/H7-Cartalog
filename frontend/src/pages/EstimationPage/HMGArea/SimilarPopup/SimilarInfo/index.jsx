import { useState, useEffect } from 'react';
import { useData } from '../../../../../utils/Context';
import { SIMILAR_INFO } from '../constants';
import { ReactComponent as LeftArrow } from '../../../../../../assets/icons/arrow_left.svg';
import { ReactComponent as RightArrow } from '../../../../../../assets/icons/arrow_right.svg';
import * as S from './style';
import HMGTag from '../../../../../components/HMGTag';

function SimilarInfo() {
  const [rightClassName, setRightClassName] = useState('');
  const [leftClassName, setLeftClassName] = useState('');
  const { summary } = useData();
  const value = 42239849;

  return (
    <S.SimilarInfo>
      <S.LeftArea>
        <S.ArrowButton className={leftClassName}>
          <LeftArrow />
        </S.ArrowButton>
        <S.Info>
          <S.SubTitle>{SIMILAR_INFO.TITLE}</S.SubTitle>
          <S.MainTitle>Le Blanc</S.MainTitle>
          {/* 해시태그 */}
          해시태그
          <S.Price>{value.toLocaleString('ko-KR')}원</S.Price>
        </S.Info>
        <img src={summary.sideImage} alt="exterior" />
      </S.LeftArea>
      <S.RightArea>
        <S.TagWrapper>
          <HMGTag type="tag20" />
        </S.TagWrapper>
        <S.OptionWrapper>
          <S.OptionTitle>{SIMILAR_INFO.OPTION}</S.OptionTitle>
        </S.OptionWrapper>
      </S.RightArea>
    </S.SimilarInfo>
  );
}

export default SimilarInfo;
