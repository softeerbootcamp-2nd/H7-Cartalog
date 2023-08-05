import * as S from './style';

function SliderMark({ minPrice, maxPrice }) {
  const minText = minPrice / 10000;
  const maxText = maxPrice / 10000;

  return (
    <S.SliderMark>
      <span>{minText}만원</span>
      <span>{maxText}만원</span>
    </S.SliderMark>
  );
}

export default SliderMark;
