import * as S from './style';
import Title from '../../../components/Title';

const TYPE = 'dark';
const SUB_TITLE = '파워트레인';
const MAIN_TITLE = '컴포트 ll';

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
