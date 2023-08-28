import * as S from './style';

function Summary({ data }) {
  return (
    <S.Summary>
      {data.map(({ title, name }) => (
        <S.TrimData key={title}>
          <S.Title>{title}</S.Title>
          <S.Name>{name}</S.Name>
        </S.TrimData>
      ))}
    </S.Summary>
  );
}

export default Summary;
