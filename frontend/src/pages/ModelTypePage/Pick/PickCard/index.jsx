import * as S from './style';
import TypeCard from '../../../../components/TypeCard';

function PickCard() {
  return (
    <S.PickCard>
      <S.TypeCardName>파워트레인</S.TypeCardName>

      <S.SelectCard>
        <TypeCard
          name="가솔린 3.8"
          pickRatio={29}
          price={280000}
          // selected={active === 0}
          // onClick={() => setActive(0)}
        />
        <TypeCard
          name="가솔린 3.8"
          pickRatio={29}
          price={280000}
          // selected={active === 0}
          // onClick={() => setActive(0)}
        />
      </S.SelectCard>
    </S.PickCard>
  );
}

export default PickCard;
