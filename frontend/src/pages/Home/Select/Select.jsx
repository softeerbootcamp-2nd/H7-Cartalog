import * as S from './SelectStyle';
import TrimCard from '../../../components/TrimCard/TrimCard';

function Select() {
  return (
    <S.Select>
      <S.Title>트림을 선택해주세요</S.Title>
      <S.Trim>
        <TrimCard name="Exclusive" description="기본에 충실한 팰리세이드" price="4,044,000" />
        <TrimCard name="Le Blanc" description="기본에 충실한 팰리세이드" price="4,044,000" />
        <TrimCard name="Prestige" description="기본에 충실한 팰리세이드" price="4,044,000" />
        <TrimCard name="Calligraphy" description="기본에 충실한 팰리세이드" price="4,044,000" />
      </S.Trim>
    </S.Select>
  );
}

export default Select;
