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
  estimateId,
  page,
  setPage,
  price,
  setPrice,
  option,
  setOption,
  optionData,
  setOptionData,
}) {
  const [similarData, setSimilarData] = useState();
  const [rightArrow, setRightArrow] = useState('');
  const [leftArrow, setLeftArrow] = useState('');
  const data = useData();

  useEffect(() => {
    const fetchData = async () => {
      const fetchedData = await fetch(
        `https://api.hyundei.shop/similarity/releases?estimateId=1&similarEstimateId=${estimateId}`,
      ).then((res) => res.json());
      setSimilarData(fetchedData);
    };

    fetchData();
  }, [estimateId]);

  useEffect(() => {
    setLeftArrow(page === 0 ? '' : 'active');
    setRightArrow(
      page !== data.estimation.similarEstimateCountInfo.similarEstimateCounts.length - 1
        ? 'active'
        : '',
    );
  }, [data.estimation.similarEstimateCountInfo.similarEstimateCounts.length, page]);

  if (!similarData) return <S.SimilarInfo />;

  return (
    <S.SimilarInfo>
      <S.LeftArea>
        <S.ArrowButton
          className={leftArrow}
          onClick={() => {
            setPage(page - 1);
            const newPrice =
              data.estimation.similarEstimateCountInfo.similarEstimateCounts[page - 1].price;
            setPrice(newPrice);
          }}
          disabled={leftArrow !== 'active'}
        >
          <LeftArrow />
        </S.ArrowButton>
        <S.LeftInfo>
          <S.SubTitle>{SIMILAR_INFO.TITLE}</S.SubTitle>
          <S.MainTitle>{similarData.trimName}</S.MainTitle>
          <S.HashTags>
            {similarData.modelTypes?.map((hashtag) => (
              <div key={hashtag}>{hashtag}</div>
            ))}
          </S.HashTags>
          <S.Price>{similarData.price?.toLocaleString('ko-KR')}원</S.Price>
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
              {similarData.nonExistentOptions?.map((nonOption) => (
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
            const newPrice =
              data.estimation.similarEstimateCountInfo.similarEstimateCounts[page + 1].price;
            setPrice(newPrice);
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
