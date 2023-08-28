import * as S from './style';

function LineInfo({ title, content, price }) {
  return (
    <S.LineInfo>
      <S.Wrapper>
        <S.Title>{title}</S.Title>
        <S.Content>{content}</S.Content>
      </S.Wrapper>
      {price && <S.Price>{`+${price.toLocaleString()}원`}</S.Price>}
    </S.LineInfo>
  );
}

export default LineInfo;
