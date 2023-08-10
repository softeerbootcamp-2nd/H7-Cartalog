import { useData, TotalPrice } from '../../../utils/Context';
import { INTERIOR_COLOR, PICK } from '../constants';
import * as S from './style';

import PickTitle from '../../../components/PickTitle';
import ColorCard from '../../../components/ColorCard';
import ColorChip from '../../../components/ColorChip';
import NextButton from '../../../components/NextButton';

function Pick() {
  const { setTrimState, interiorColor, price } = useData();

  // !FIX 안넘겨주고 컴포넌트에서 처리해도 될듯
  const nextButtonProps = {
    totalPrice: TotalPrice(price),
    estimateEvent: '',
    nextEvent: '',
  };

  console.log(interiorColor);

  const pickTitleProps = { mainTitle: PICK.TITLE };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />

      <S.Color>
        {interiorColor.dataFetch.map((color) => (
          <ColorCard
            key={color.id}
            pickRatio={color.chosen}
            name={color.name}
            price={color.price}
            selected={interiorColor.pick === color.id}
            onClick={() =>
              setTrimState((prevState) => ({
                ...prevState,
                interiorColor: {
                  ...prevState.interiorColor,
                  pick: color.id,
                  pickName: color.name,
                  pickCarImageUrl: color.carImageUrl,
                },
                price: {
                  ...prevState.price,
                  interiorColorPrice: color.price,
                },
              }))
            }
          >
            <ColorChip
              selected={interiorColor.pick === color.id}
              src={color.colorImageUrl}
              type={INTERIOR_COLOR.TYPE}
            />
          </ColorCard>
        ))}
      </S.Color>

      <S.Footer>
        <S.FooterEnd>
          <NextButton {...nextButtonProps} />
        </S.FooterEnd>
      </S.Footer>
    </S.Pick>
  );
}

export default Pick;
