import * as S from './LogoStyle';
import { ReactComponent as ArrowDown } from '../../../../assets/icons/arrow_down.svg';
import { ReactComponent as HyundaiLogo } from '../../../../assets/images/Hyundai_logo.svg';

function Logo() {
  return (
    <S.Logo>
      <HyundaiLogo width="39px" height="22px" />
      <S.Line />
      <S.CarType>
        <S.Type>펠리세이드</S.Type>
        <ArrowDown width="12px" />
      </S.CarType>
    </S.Logo>
  );
}

export default Logo;
