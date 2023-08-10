import { useData } from '../../../utils/Context';
import { INFO } from '../constants';
import * as S from './style';
import Title from '../../../components/Title';

function Info() {
  const { interiorColor } = useData();
  const TitleProps = {
    type: INFO.TYPE,
    subTitle: INFO.SUB_TITLE,
    mainTitle: interiorColor.pickName,
  };

  return (
    <S.Info>
      <Title {...TitleProps} />
    </S.Info>
  );
}

export default Info;
