import { useData } from '../../../utils/Context';
import { PICK } from '../constants';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import TrimCard from './TrimCard';

function Pick() {
  const { setTrimState, trim } = useData();
  const pickTitleProps = { mainTitle: PICK.TITLE };
  const handleTrimCardClick = (trimData) => {
    if (trimData.id === trim.id) return;

    setTrimState((prevState) => ({
      ...prevState,
      trim: {
        ...prevState.trim,
        id: trimData.id,
        isDefault: false,
      },
      modelType: {
        ...prevState.modelType,
        isFetch: false,
      },
      exteriorColor: {
        ...prevState.exteriorColor,
        isFetch: false,
      },
      interiorColor: {
        ...prevState.interiorColor,
        isFetch: false,
      },
      optionPicker: {
        ...prevState.optionPicker,
        isFetch: false,
      },
      estimation: {
        ...prevState.estimation,
        isFetch: false,
      },
    }));
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.Trim>
        {trim.fetchData.map((trimData) => {
          return (
            <TrimCard
              key={trimData.id}
              name={trimData.name}
              description={trimData.description}
              price={trimData.minPrice}
              defaultInfo={trimData.defaultInfo}
              active={trimData.id === trim.id}
              onClick={() => handleTrimCardClick(trimData)}
            />
          );
        })}
      </S.Trim>
    </S.Pick>
  );
}

export default Pick;
