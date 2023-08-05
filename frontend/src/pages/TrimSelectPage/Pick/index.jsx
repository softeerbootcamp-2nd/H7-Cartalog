import * as S from './style';
import TrimCard from './TrimCard';

function Pick({ nextPage }) {
  return (
    <S.Pick>
      <S.Title>트림을 선택해주세요</S.Title>
      <S.Trim>
        <TrimCard
          name="Exclusive"
          description="기본에 충실한 팰리세이드"
          price="4,044,000"
          nextPage={nextPage}
        />
        <TrimCard
          name="Le Blanc"
          description="기본에 충실한 팰리세이드"
          price="4,044,000"
          nextPage={nextPage}
        />
        <TrimCard
          name="Prestige"
          description="기본에 충실한 팰리세이드"
          price="4,044,000"
          nextPage={nextPage}
        />
        <TrimCard
          name="Calligraphy"
          description="기본에 충실한 팰리세이드"
          price="4,044,000"
          nextPage={nextPage}
        />
      </S.Trim>
    </S.Pick>
  );
}

export default Pick;
