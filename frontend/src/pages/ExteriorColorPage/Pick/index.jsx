import { useData } from '../../../utils/Context';
import { EXTERIOR_COLOR, PICK } from '../constants';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import ColorCard from '../../../components/ColorCard';
import ColorChip from '../../../components/ColorChip';
import NextButton from '../../../components/NextButton';

function Pick() {
  const { setTrimState, exteriorColor } = useData();
  const pickTitleProps = { mainTitle: PICK.TITLE };

  const handleColorCardClick = (exterior) => {
    setTrimState((prevState) => ({
      ...prevState,
      exteriorColor: {
        ...prevState.exteriorColor,
        exteriorColorCode: exterior.code,
        exteriorColorName: exterior.name,
        exteriorColorCarImageUrl: exterior.carImageUrl,
      },
      price: {
        ...prevState.price,
        exteriorColorPrice: exterior.price,
      },
    }));
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.Color>
        {exteriorColor.exteriorColorFetch.map((exterior) => (
          <ColorCard
            key={exterior.code}
            pickRatio={exterior.chosen}
            name={exterior.name}
            price={exterior.price}
            selected={exteriorColor.exteriorColorCode === exterior.code}
            onClick={() => handleColorCardClick(exterior)}
          >
            <ColorChip
              selected={exteriorColor.exteriorColorCode === exterior.code}
              src={exterior.colorImageUrl}
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
