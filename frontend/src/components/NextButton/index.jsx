import { useState } from 'react';
import { useData, NextPage, TotalPrice } from '../../utils/Context';
import { TOTAL, BUTTON, ROUND_BUTTON } from './constants';
import * as S from './style';
import RoundButton from '../RoundButton';
import Button from '../Button';
import SummaryPopup from '../SummaryPopup';

function NextButton() {
  const [show, setShow] = useState(false);
  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);
  const { setTrimState, price } = useData();

  const buttonProps = {
    type: BUTTON.TYPE,
    state: BUTTON.STATE,
    mainTitle: BUTTON.MAIN_TITLE,
    event: () => NextPage(setTrimState),
  };

  const roundButtonProps = {
    type: ROUND_BUTTON.TYPE,
    state: ROUND_BUTTON.STATE,
    title: ROUND_BUTTON.TITLE,
    clickEvent: handleShow,
  };

  return (
    <S.NextButton>
      <S.Estimate>
        <RoundButton {...roundButtonProps} />
        <S.TotalPrice>
          <S.TotalPriceText>{TOTAL.PRICE}</S.TotalPriceText>
          <S.TotalPriceNumber>
            {TotalPrice(price).toLocaleString('ko-KR')}
            {TOTAL.WON}
          </S.TotalPriceNumber>
        </S.TotalPrice>
      </S.Estimate>
      <Button {...buttonProps} />
      <SummaryPopup show={show} close={handleClose} onClick={() => {}} />
    </S.NextButton>
  );
}

export default NextButton;
