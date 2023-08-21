import * as S from './style';

function SliderMark({ minPrice, maxPrice, budget }) {
  const budgetText = budget / 10000;
  const minText = minPrice / 10000;
  const maxText = maxPrice / 10000;

  return (
    <S.SliderMark>
      <span>{minText}만원</span>
      <S.SliderMarkText>예산: {budgetText}만원</S.SliderMarkText>
      <span>{maxText}만원</span>
    </S.SliderMark>
  );
}

export default SliderMark;
