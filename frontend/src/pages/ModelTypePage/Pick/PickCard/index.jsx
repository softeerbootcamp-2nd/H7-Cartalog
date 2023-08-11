import * as S from './style';
import TypeCard from '../../../../components/TypeCard';

function PickCard({ data }) {
  console.log(data);
  return (
    <S.PickCard>
      <S.TypeCardName>{data.type}</S.TypeCardName>

      <S.SelectCard>
        {data.options.map((option) => (
          <TypeCard
            key={option.id}
            name={option.name}
            pickRatio={option.chosen}
            price={option.price}
            // selected={active === option.id}
            // onClick={() => setActive(0)}

            // active={trimData.id === trim.trimId}
            // onClick={() => {
            //   if (trimData.id === trim.trimId) return;
            //   setTrimState((prevState) => ({
            //     ...prevState,
            //     trim: {
            //       ...prevState.trim,
            //       trimId: trimData.id,
            //     },
            //   }));
            // }}
          />
        ))}
      </S.SelectCard>
    </S.PickCard>
  );
}

export default PickCard;
