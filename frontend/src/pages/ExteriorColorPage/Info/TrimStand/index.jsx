import * as S from './style';
import { TRIM_STAND } from '../../constants';

function TrimStand() {
  return (
    <S.TrimStand>
      <S.Stand>{TRIM_STAND.DEGREE}</S.Stand>
    </S.TrimStand>
  );
}

export default TrimStand;
