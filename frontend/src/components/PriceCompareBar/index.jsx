import * as S from './style';

function PriceCompareBar({ min, max, my, value }) {
  const difference = Math.abs(my - value);
  const comparisonText = my > value ? '싸요' : '비싸요';
  const minText = `${Math.round(min / 10000)}만원`;
  const maxText = `${Math.round(max / 10000)}만원`;

  return (
    <S.PriceCompareBar>
      <S.Info>
        <S.Title>유사견적 가격</S.Title>
        <S.Description>
          내 견적보다 <span>{difference.toLocaleString()}</span>원 {comparisonText}
        </S.Description>
      </S.Info>
      <S.Identifiers>
        <S.Identifier $active>내 견적</S.Identifier>
        <S.Identifier>유사견적</S.Identifier>
      </S.Identifiers>
      <S.BarArea>
        <S.Bar>
          <S.TextWrapper>
            <div>{minText}</div>
            <div>{maxText}</div>
          </S.TextWrapper>
          <S.Pin
            $active
            style={{ transform: `translateX(${((my - min) / (max - min)) * 342}px)` }}
          />
          <S.Pin style={{ transform: `translateX(${((value - min) / (max - min)) * 342}px)` }} />
        </S.Bar>
      </S.BarArea>
    </S.PriceCompareBar>
  );
}

export default PriceCompareBar;
