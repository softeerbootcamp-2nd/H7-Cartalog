import * as S from './style';
import { TOTAL, BUTTON, ROUND_BUTTON } from './constants';
import RoundButton from '../RoundButton';
import Button from '../Button';

function NextButton({ totalPrice, estimateEvent, nextEvent }) {
  const buttonProps = {
    type: BUTTON.TYPE,
    state: BUTTON.STATE,
    mainTitle: BUTTON.MAIN_TITLE,
  };

  const roundButtonProps = {
    type: ROUND_BUTTON.TYPE,
    state: ROUND_BUTTON.STATE,
    title: ROUND_BUTTON.TITLE,
  };

  return (
    <S.NextButton>
      <S.Estimate>
        <RoundButton onClick={estimateEvent} {...roundButtonProps} />
        <S.TotalPrice>
          <S.TotalPriceText>{TOTAL.PRICE}</S.TotalPriceText>
          <S.TotalPriceNumber>
            {totalPrice.toLocaleString('ko-KR')}
            {TOTAL.WON}
          </S.TotalPriceNumber>
        </S.TotalPrice>
      </S.Estimate>
      <Button onClick={nextEvent} {...buttonProps} />
    </S.NextButton>
  );
}

export default NextButton;
