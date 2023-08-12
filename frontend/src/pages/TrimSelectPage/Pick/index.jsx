import { useData } from '../../../utils/Context';
import { PICK } from '../constants';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import TrimCard from './TrimCard';

function Pick() {
  const { setTrimState, trim } = useData();
  const pickTitleProps = { mainTitle: PICK.TITLE };
  const handleTrimCardClick = (trimData) => {
    if (trimData.id === trim.trimId) return;
    setTrimState((prevState) => ({
      ...prevState,
      trim: {
        ...prevState.trim,
        trimId: trimData.id,
      },
    }));
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.Trim>
        {trim.trimFetch.map((trimData) => {
          return (
            <TrimCard
              key={trimData.id}
              name={trimData.name}
              description={trimData.description}
              price={trimData.minPrice}
              defaultInfo={trimData.defaultInfo}
              active={trimData.id === trim.trimId}
              onClick={() => handleTrimCardClick(trimData)}
            />
          );
        })}
      </S.Trim>
    </S.Pick>
  );
}

export default Pick;
