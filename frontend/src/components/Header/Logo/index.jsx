import * as S from './style';
import { LOGO } from '../constants';
import { ReactComponent as ArrowDown } from '../../../../assets/icons/arrow_down.svg';
import { ReactComponent as HyundaiLogo } from '../../../../assets/images/Hyundai_logo.svg';

function Logo() {
  return (
    <S.Logo>
      <HyundaiLogo />
      <S.Line />
      <S.CarType>
        <S.Type>{LOGO.TYPE}</S.Type>
        <ArrowDown />
      </S.CarType>
    </S.Logo>
  );
}

export default Logo;
