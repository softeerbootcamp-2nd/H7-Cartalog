import * as S from './ExitStyle';
import { ReactComponent as Cancel } from '../../../../assets/icons/cancel.svg';

function Exit() {
  return (
    <S.Exit>
      <S.Text>종료</S.Text>
      <Cancel width="12px" />
    </S.Exit>
  );
}

export default Exit;
