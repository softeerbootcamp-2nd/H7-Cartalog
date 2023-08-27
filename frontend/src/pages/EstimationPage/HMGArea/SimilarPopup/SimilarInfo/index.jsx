import { useState, useEffect } from 'react';
import { useData } from '../../../../../utils/Context';
import { SIMILAR_INFO } from '../constants';
import { ReactComponent as LeftArrow } from '../../../../../../assets/icons/arrow_left.svg';
import { ReactComponent as RightArrow } from '../../../../../../assets/icons/arrow_right.svg';
import * as S from './style';
import HMGTag from '../../../../../components/HMGTag';
import SimilarCard from '../SimilarCard';

function SimilarInfo({
  info,
  page,
  setPage,
  setPrice,
  option,
  setOption,
  optionData,
  setOptionData,
}) {
  const [rightArrow, setRightArrow] = useState('');
  const [leftArrow, setLeftArrow] = useState('');
  const data = useData();

  useEffect(() => {
    setLeftArrow(page === 0 ? '' : 'active');
    setRightArrow(
      page !== data.estimation.similarEstimateCountInfo.similarEstimateCounts.length - 1
        ? 'active'
        : '',
    );
  }, [data.estimation.similarEstimateCountInfo.similarEstimateCounts.length, page]);

  return (
    <S.SimilarInfo>
      <S.LeftArea>
        <S.ArrowButton
          className={leftArrow}
          onClick={() => {
            setPage(page - 1);
            setPrice(
              data.estimation.similarEstimateCountInfo.similarEstimateCounts[page - 1].price ?? 0,
            );
          }}
          disabled={leftArrow !== 'active'}
        >
          <LeftArrow />
        </S.ArrowButton>
        <S.LeftInfo>
          <S.SubTitle>{SIMILAR_INFO.TITLE}</S.SubTitle>
          <S.MainTitle>{info.trimName}</S.MainTitle>
          <S.HashTags>
            {info.modelTypes?.slice(0, 3).map((hashtag) => (
              <div key={hashtag.id}>{hashtag.name}</div>
            ))}
          </S.HashTags>
          <S.Price>{info.price?.toLocaleString('ko-KR')}원</S.Price>
        </S.LeftInfo>
        <img src={info.exteriorCarSideImageUrl} alt="exterior" />
      </S.LeftArea>
      <S.RightArea>
        <S.RightInfo>
          <S.TagWrapper>
            <HMGTag type="tag20" />
          </S.TagWrapper>
          <S.OptionWrapper>
            <S.OptionTitle>{SIMILAR_INFO.OPTION}</S.OptionTitle>
            <S.CardWrapper>
              {info.nonExistentOptions?.map((nonOption) => (
                <SimilarCard
                  key={nonOption.optionId}
                  name={nonOption.name}
                  price={nonOption.price}
                  imageUrl={nonOption.imageUrl}
                  selected={
                    option.includes(nonOption.optionId) ||
                    data.optionPicker.chosenOptions.includes(nonOption.optionId)
                  }
                  onClick={() => {
                    if (option.includes(nonOption.optionId)) {
                      setOption(option.filter((id) => id !== nonOption.optionId));
                      setOptionData(optionData.filter((nonData) => nonData !== nonOption));
                    } else {
                      setOption([...option, nonOption.optionId]);
                      setOptionData([...optionData, nonOption]);
                    }
                  }}
                />
              ))}
            </S.CardWrapper>
          </S.OptionWrapper>
        </S.RightInfo>
        <S.ArrowButton
          className={rightArrow}
          onClick={() => {
            setPage(page + 1);
            setPrice(data.estimation.similarEstimateCountInfo[page + 1].price);
          }}
          disabled={rightArrow !== 'active'}
        >
          <RightArrow />
        </S.ArrowButton>
      </S.RightArea>
    </S.SimilarInfo>
  );
}

export default SimilarInfo;
