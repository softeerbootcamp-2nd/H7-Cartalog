import { useState, useEffect } from 'react';
import { useData } from '../../../../../utils/Context';
import { SIMILAR_INFO } from '../constants';
import { ReactComponent as LeftArrow } from '../../../../../../assets/icons/arrow_left.svg';
import { ReactComponent as RightArrow } from '../../../../../../assets/icons/arrow_right.svg';
import * as S from './style';
import HMGTag from '../../../../../components/HMGTag';
import SimilarCard from '../SimilarCard';

function SimilarInfo() {
  const [rightArrow, setRightArrow] = useState('');
  const [leftArrow, setLeftArrow] = useState('');
  const { summary } = useData();
  const value = 42239849;

  return (
    <S.SimilarInfo>
      <S.LeftArea>
        <S.ArrowButton className={leftArrow}>
          <LeftArrow />
        </S.ArrowButton>
        <S.LeftInfo>
          <S.SubTitle>{SIMILAR_INFO.TITLE}</S.SubTitle>
          <S.MainTitle>Le Blanc</S.MainTitle>
          <S.HashTags>
            {['하이', '바보', '크롱'].slice(0, 3).map((hashtag) => (
              <div key={hashtag}>{hashtag}</div>
            ))}
          </S.HashTags>
          <S.Price>{value.toLocaleString('ko-KR')}원</S.Price>
        </S.LeftInfo>
        <img src={summary.sideImage} alt="exterior" />
      </S.LeftArea>
      <S.RightArea>
        <S.RightInfo>
          <S.TagWrapper>
            <HMGTag type="tag20" />
          </S.TagWrapper>
          <S.OptionWrapper>
            <S.OptionTitle>{SIMILAR_INFO.OPTION}</S.OptionTitle>
            {/* 옵션카드 */}
            <S.CardWrapper>
              <SimilarCard
                //   key={exterior.code}
                name="빌트인 캠"
                price={350000}
                //   selected={exteriorColor.code === exterior.code}
                onClick={() => {}}
                imageUrl={summary.sideImage}
              />
              <SimilarCard
                //   key={exterior.code}
                name="빌트인 캠"
                price={350000}
                //   selected={exteriorColor.code === exterior.code}
                onClick={() => {}}
                imageUrl={summary.sideImage}
              />
            </S.CardWrapper>
          </S.OptionWrapper>
        </S.RightInfo>
        <S.ArrowButton className={rightArrow}>
          <RightArrow />
        </S.ArrowButton>
      </S.RightArea>
    </S.SimilarInfo>
  );
}

export default SimilarInfo;
