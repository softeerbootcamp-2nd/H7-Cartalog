import { useEffect, useState } from 'react';
import * as S from './style';
import Title from '../../../components/Title';
import Description from './Description';
import ImageViewButton from '../../../components/ImageViewButton';
import { URL } from '../../../constants';

const TYPE = 'dark';
const CATEGORY = '파워트레인/성능';

function Info({ optionId, expanded, setExpanded }) {
  const [optionInfo, setOptionInfo] = useState();
  const [imageUrl, setImageUrl] = useState();
  const imageButtonText = expanded ? '이미지 접기' : '이미지 확인';
  const TitleProps = {
    type: TYPE,
    subTitle: optionInfo?.category ?? CATEGORY,
    mainTitle: optionInfo?.name,
    info: <Description optionInfo={optionInfo} setImageUrl={setImageUrl} />,
  };

  useEffect(() => {
    if (!optionId) return;
    async function fetchData() {
      const response = await fetch(`${URL}models/trims/options/detail?optionId=${optionId}`);
      const data = await response.json();
      setOptionInfo(data);
      setImageUrl(data.imageUrl);
    }
    fetchData();
  }, [optionId]);

  if (!optionInfo) return <div />;

  return (
    <S.Info>
      <S.ModelText>
        <Title {...TitleProps} />
      </S.ModelText>
      <S.ModelImage src={imageUrl} />
      <ImageViewButton text={imageButtonText} onClick={() => setExpanded((prev) => !prev)} />
    </S.Info>
  );
}

export default Info;
