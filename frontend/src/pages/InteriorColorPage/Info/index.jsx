import { INFO } from '../constants';
import * as S from './style';
import Title from '../../../components/Title';

function Info({ interiorColor }) {
  const TitleProps = {
    type: INFO.TYPE,
    subTitle: INFO.SUB_TITLE,
    mainTitle: interiorColor.name,
  };

  return (
    <S.Info>
      <Title {...TitleProps} />
    </S.Info>
  );
}

export default Info;
