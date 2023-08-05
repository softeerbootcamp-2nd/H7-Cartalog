import * as S from './style';

function Thumb({ value }) {
  return (
    <S.Thumb value={value}>
      <S.ThumbCircle />
    </S.Thumb>
  );
}

export default Thumb;
