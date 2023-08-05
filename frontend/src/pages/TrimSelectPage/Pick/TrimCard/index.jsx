import * as S from './style';

function TrimCard({ name, description, price, active, onClick, nextPage }) {
  return (
    <S.TrimCard className={active ? 'active' : null} onClick={onClick}>
      <S.Trim>
        <S.Description>{description}</S.Description>
        <S.Name>{name}</S.Name>
      </S.Trim>
      <S.Price>{price}</S.Price>
      <S.SelectButton onClick={nextPage}>선택하기</S.SelectButton>
    </S.TrimCard>
  );
}

export default TrimCard;
