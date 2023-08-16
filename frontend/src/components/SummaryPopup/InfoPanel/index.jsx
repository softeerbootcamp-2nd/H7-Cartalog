import * as S from './style';

function InfoPanel({ data }) {
  const info = data.reduce((acc, cur, index) => {
    if (index !== 0) acc.push(<S.Divider key={cur} />);
    return acc.concat(
      cur.map((line) => (
        <S.LineInfo key={line.title}>
          <S.Wrapper>
            <S.Title>{line.title}</S.Title>
            <S.Content>{line.content}</S.Content>
          </S.Wrapper>
          {line.price !== undefined && <S.Price>+{line.price.toLocaleString()}원</S.Price>}
        </S.LineInfo>
      )),
    );
  }, []);

  return <S.InfoPanel>{info}</S.InfoPanel>;
}

export default InfoPanel;
