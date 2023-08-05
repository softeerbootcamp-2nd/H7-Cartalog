import * as S from './style';
import Title from '../../../components/Title';

const TYPE = 'dark';
const MAIN_TITLE = 'Le Blanc';

function Info() {
  const TitleProps = {
    type: TYPE,
    mainTitle: MAIN_TITLE,
  };

  return (
    <S.Info>
      <Title {...TitleProps} />
    </S.Info>
  );
}

export default Info;
