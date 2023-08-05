import * as S from './style';
import Button from '../../../../components/Button';

const TYPE = 'buttonC';
const STATE = 'active';
const MAIN_TITLE = '선택하기';

function TrimCard({ name, description, price, active, onClick, nextPage }) {
  const buttonProps = {
    nextPage: nextPage,
    type: TYPE,
    state: STATE,
    mainTitle: MAIN_TITLE,
  };

  return (
    <S.TrimCard className={active ? 'active' : null} onClick={onClick}>
      <S.Trim>
        <S.Description>{description}</S.Description>
        <S.Name>{name}</S.Name>
      </S.Trim>
      <S.Price>{price}</S.Price>
      <S.SelectButton>
        <Button {...buttonProps}></Button>
      </S.SelectButton>
    </S.TrimCard>
  );
}

export default TrimCard;
