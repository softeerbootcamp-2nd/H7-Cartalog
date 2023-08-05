import * as S from './style';

const TITLE = '최고출력(PS/rpm)';
const OUTPUT = '202/3,800';

function HMGData() {
  return (
    <S.HMGData>
      <S.Title>{TITLE}</S.Title>
      <S.Output>{OUTPUT}</S.Output>
    </S.HMGData>
  );
}

export default HMGData;
