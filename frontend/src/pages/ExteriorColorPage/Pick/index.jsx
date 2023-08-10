import { useData } from '../../../utils/Context';
import { EXTERIOR_COLOR, PICK } from '../constants';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import ColorCard from '../../../components/ColorCard';
import ColorChip from '../../../components/ColorChip';
import NextButton from '../../../components/NextButton';

function Pick() {
  // const navigate = useNavigate();
  const { setTrimState, exteriorColor } = useData();
  const pickTitleProps = { mainTitle: PICK.TITLE };

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
                  pickName: color.name,
                  pickCarImageUrl: color.carImageUrl,
                },
                price: {
                  ...prevState.price,
                  exteriorColorPrice: color.price,
                },
              }))
            }
          >
            <ColorChip
              selected={exteriorColor.pick === color.id}
              src={color.colorImageUrl}
              type={EXTERIOR_COLOR.TYPE}
            />
          </ColorCard>
        ))}
      </S.Color>

      <S.Footer>
        <S.FooterEnd>
          <NextButton />
        </S.FooterEnd>
      </S.Footer>
    </S.Pick>
  );
}

export default Pick;
