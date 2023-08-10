import * as S from './style';
import { EXIT } from '../constants';
import { ReactComponent as Cancel } from '../../../../assets/icons/cancel.svg';

function Exit() {
  return (
    <S.Exit>
      <S.Text>{EXIT.TEXT}</S.Text>
      <Cancel />
    </S.Exit>
  );
}

export default Exit;
