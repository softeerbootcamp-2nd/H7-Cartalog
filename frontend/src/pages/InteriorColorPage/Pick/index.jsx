import { useData } from '../../../utils/Context';
import { INTERIOR_COLOR, PICK } from '../constants';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import ColorCard from '../../../components/ColorCard';
import ColorChip from '../../../components/ColorChip';
import NextButton from '../../../components/NextButton';

function Pick() {
  const { setTrimState, interiorColor } = useData();
  const pickTitleProps = { mainTitle: PICK.TITLE };

  const handleColorCardClick = (interior) => {
    setTrimState((prevState) => ({
      ...prevState,
      interiorColor: {
        ...prevState.interiorColor,
        code: interior.code,
        name: interior.name,
        carImageUrl: interior.carImageUrl,
      },
      price: {
        ...prevState.price,
        interiorColorPrice: interior.price,
      },
    }));
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />

      <S.Color>
        {interiorColor.fetchData.map((interior) => (
          <ColorCard
            key={interior.code}
            pickRatio={interior.chosen}
            name={interior.name}
            price={interior.price}
            selected={interiorColor.code === interior.code}
            onClick={() => handleColorCardClick(interior)}
          >
            <ColorChip
              selected={interiorColor.code === interior.code}
              src={interior.colorImageUrl}
              type={INTERIOR_COLOR.TYPE}
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
