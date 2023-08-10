import { useNavigate } from 'react-router-dom';
import { useData, TotalPrice } from '../../utils/Context';
import { TOTAL, BUTTON, ROUND_BUTTON, PAGE } from './constants';
import * as S from './style';
import RoundButton from '../RoundButton';
import Button from '../Button';

function NextButton() {
  const { page, price } = useData();
  const navigate = useNavigate();

  const buttonProps = {
    type: BUTTON.TYPE,
    state: BUTTON.STATE,
    mainTitle: BUTTON.MAIN_TITLE,
    event: () => {
      navigate(PAGE.find((type) => type.page === page + 1).to);
    },
  };

  const roundButtonProps = {
    type: ROUND_BUTTON.TYPE,
    state: ROUND_BUTTON.STATE,
    title: ROUND_BUTTON.TITLE,
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
    </S.NextButton>
  );
}

export default NextButton;
