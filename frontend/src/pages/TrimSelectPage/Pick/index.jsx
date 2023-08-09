import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import TrimCard from './TrimCard';

const PICK_MAIN_TITLE = '트림을 선택해주세요.';

function Pick({ trimId, setTrimState, data }) {
  const pickTitleProps = {
    mainTitle: PICK_MAIN_TITLE,
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.Trim>
        {data?.map((trim) => {
          const handleClick = () => {
            if (trimId === trim.id) return;
            setTrimState({ trimId: trim.id });
          };

          return (
            <TrimCard
              key={trim.id}
              name={trim.name}
              description={trim.description}
              price={trim.minPrice}
              active={trimId === trim.id}
              onClick={handleClick}
            />
          );
        })}
      </S.Trim>
    </S.Pick>
  );
}

export default Pick;
