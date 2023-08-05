import * as S from './style';
import Title from '../../../components/Title';

const TYPE = 'dark';
const SUB_TITLE = '외장색상';
const MAIN_TITLE = '어비스블랙펄';

function Info() {
  const TitleProps = {
    type: TYPE,
    subTitle: SUB_TITLE,
    mainTitle: MAIN_TITLE,
  };

  return (
    <S.Info>
      <Title {...TitleProps} />
    </S.Info>
  );
}

export default Info;
