import { useEffect, useState } from 'react';
import * as S from './style';
import Title from '../../../components/Title';
import Description from './Description';

const TYPE = 'dark';
const CATEGORY = '파워트레인/성능';

function Info({ optionId }) {
  const [optionInfo, setOptionInfo] = useState();
  const [imageUrl, setImageUrl] = useState();
  const TitleProps = {
    type: TYPE,
    subTitle: optionInfo?.category ?? CATEGORY,
    mainTitle: optionInfo?.name,
    info: <Description optionInfo={optionInfo} setImageUrl={setImageUrl} />,
  };

  useEffect(() => {
    if (!optionId) return;
    async function fetchData() {
      const response = await fetch(
        `http://3.36.126.30/models/trims/options/detail?optionId=${optionId}`,
      );
      const data = await response.json();
      setOptionInfo(data);
      setImageUrl(data.imageUrl);
    }
    fetchData();
  }, [optionId]);

  if (!optionInfo) return <div>loading...</div>;

  return (
    <S.Info>
      <S.ModelText>
        <Title {...TitleProps} />
      </S.ModelText>
      <S.ModelImage src={imageUrl} />
    </S.Info>
  );
}

export default Info;
