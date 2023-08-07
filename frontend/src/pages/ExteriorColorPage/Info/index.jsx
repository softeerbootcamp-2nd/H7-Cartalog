import { useState } from 'react';
import * as S from './style';
import Title from '../../../components/Title';
import TrimImage from './TrimImage';

const TYPE = 'dark';
const SUB_TITLE = '외장색상';
const MAIN_TITLE = '어비스블랙펄';

function Info() {
  const [start, setStart] = useState(false);
  document.addEventListener('mouseup', () => setStart(false));

  const TrimImageProps = { start };
  const TitleProps = {
    type: TYPE,
    subTitle: SUB_TITLE,
    mainTitle: MAIN_TITLE,
  };

  return (
    <S.Info onMouseDown={() => setStart(true)}>
      <Title {...TitleProps} />
      <TrimImage {...TrimImageProps} />
    </S.Info>
  );
}

export default Info;
