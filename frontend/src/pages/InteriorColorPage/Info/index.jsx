import * as S from './style';
import Title from '../../../components/Title';

const TYPE = 'light';
const SUB_TITLE = '내장색상';
const MAIN_TITLE = '퀼팅천연(블랙)';

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
