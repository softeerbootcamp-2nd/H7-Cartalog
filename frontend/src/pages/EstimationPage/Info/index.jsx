import Summary from './Summary';
import * as S from './style';

const TRIM_DATA = [
  {
    title: '모델',
    name: 'Le Blanc (르블랑)',
  },
  {
    title: '평균연비',
    name: '10.5km/l',
  },
  {
    title: '배기량',
    name: '2,199cc',
  },
];

function Info() {
  return (
    <S.InfoWrapper>
      <S.Info>
        <div>
          합리적인 가격으로 완성된
          <br /> 나만의 팰리세이드가 탄생했어요!
        </div>
        <Summary data={TRIM_DATA} />
      </S.Info>
    </S.InfoWrapper>
  );
}

export default Info;
