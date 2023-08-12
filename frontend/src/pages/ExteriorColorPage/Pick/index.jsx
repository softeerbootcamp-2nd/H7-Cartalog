import { useData } from '../../../utils/Context';
import { EXTERIOR_COLOR, PICK } from '../constants';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import ColorCard from '../../../components/ColorCard';
import ColorChip from '../../../components/ColorChip';
import NextButton from '../../../components/NextButton';
import NextColor from '../../../components/NextColor';

function Pick() {
  const { setTrimState, exteriorColor } = useData();
  const pickTitleProps = { mainTitle: PICK.TITLE };
  const colorProps = { $position: exteriorColor.position };

  const handleColorCardClick = (exterior) => {
    setTrimState((prevState) => ({
      ...prevState,
      exteriorColor: {
        ...prevState.exteriorColor,
        code: exterior.code,
        name: exterior.name,
        carImageUrl: exterior.carImageUrl,
      },
      price: {
        ...prevState.price,
        exteriorColorPrice: exterior.price,
      },
    }));
  };

  return (
    <S.Pick>
      <S.Header>
        <PickTitle {...pickTitleProps} />
        <NextColor />
      </S.Header>

      <S.ColorSet>
        <S.Color {...colorProps}>
          {exteriorColor.fetchData.map((exterior) => (
            <ColorCard
              key={exterior.code}
              pickRatio={exterior.chosen}
              name={exterior.name}
              price={exterior.price}
              selected={exteriorColor.code === exterior.code}
              onClick={() => handleColorCardClick(exterior)}
            >
              <ColorChip
                selected={exteriorColor.code === exterior.code}
                src={exterior.colorImageUrl}
                type={EXTERIOR_COLOR.TYPE}
              />
            </ColorCard>
          ))}
        </S.Color>
      </S.ColorSet>

      <S.Footer>
        <S.FooterEnd>
          <NextButton />
        </S.FooterEnd>
      </S.Footer>
    </S.Pick>
  );
}

export default Pick;
