import { useData, TotalPrice } from '../../../utils/Context';
import { PICK_TITLE } from '../constants';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import ColorCard from '../../../components/ColorCard';
import ColorChip from '../../../components/ColorChip';
import NextButton from '../../../components/NextButton';

function Pick() {
  const { setTrimState, exteriorColor, price } = useData();
  const pickTitleProps = { mainTitle: PICK_TITLE };

  const nextButtonProps = {
    totalPrice: TotalPrice(price),
    estimateEvent: '',
    nextEvent: '',
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.Color>
        {exteriorColor.dataFetch.map((color) => (
          <ColorCard
            key={color.id}
            pickRatio={color.chosen}
            name={color.name}
            price={color.price}
            selected={exteriorColor.pick === color.id}
            onClick={() =>
              setTrimState((prevState) => ({
                ...prevState,
                exteriorColor: {
                  ...prevState.exteriorColor,
                  pick: color.id,
                },
                price: {
                  ...prevState.price,
                  exteriorColorPrice: color.price,
                },
              }))
            }
          >
            <ColorChip selected={exteriorColor.pick === color.id} src={color.colorImageUrl} />
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
