import { useState } from 'react';
import * as S from './TrimCardStyle';

function TrimCard({ name, description, price }) {
  const [active, setActive] = useState(false);

  return (
    <S.TrimCard className={active ? 'active' : null} onClick={() => setActive(!active)}>
      <S.Trim>
        <S.Description>{description}</S.Description>
        <S.Name>{name}</S.Name>
      </S.Trim>
      <S.Price>{price}</S.Price>
      <S.SelectButton>선택하기</S.SelectButton>
    </S.TrimCard>
  );
}

export default TrimCard;
