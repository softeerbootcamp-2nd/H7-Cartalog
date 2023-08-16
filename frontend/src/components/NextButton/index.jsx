import { useNavigate } from 'react-router-dom';
import { createPortal } from 'react-dom';
import { useData, TotalPrice, StartAnimation } from '../../utils/Context';
import { TOTAL, BUTTON, ROUND_BUTTON, PAGE } from './constants';
import * as S from './style';
import RoundButton from '../RoundButton';
import Button from '../Button';
import SummaryPopup from '../SummaryPopup';
import { useState } from 'react';

function NextButton() {
  const [show, setShow] = useState(false);
  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);
  const navigate = useNavigate();
  const { setTrimState, page, price, pagePath } = useData();

  const buttonProps = {
    type: BUTTON.TYPE,
    state: BUTTON.STATE,
    mainTitle: BUTTON.MAIN_TITLE,
    event: () => {
      const nowPath = PAGE.find((type) => type.page === page).to;
      const nextPath = PAGE.find((type) => type.page === page + 1).to;
      StartAnimation(nowPath, nextPath, navigate, setTrimState, pagePath);
    },
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
