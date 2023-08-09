import * as S from './style';

function HMGUnit({ title, unit }) {
  return (
    <S.HMGUnit>
      <S.Detail>
        <S.DetailTitle>{title}</S.DetailTitle>
        <S.DetailUnit>{unit}</S.DetailUnit>
      </S.Detail>
    </S.HMGUnit>
  );
}

export default HMGUnit;
