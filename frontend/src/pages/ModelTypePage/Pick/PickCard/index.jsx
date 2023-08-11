import { useData } from '../../../../utils/Context';
import { PICK_CARD } from '../../constants';
import * as S from './style';
import TypeCard from '../../../../components/TypeCard';

function PickCard({ data }) {
  const { setTrimState, modelType } = useData();

  const updateTrimState = (key, idKey, priceKey, option) => {
    if (modelType[idKey] === option.id) return;
    setTrimState((prevState) => ({
      ...prevState,
      modelType: {
        ...prevState.modelType,
        [idKey]: option.id,
      },
      price: {
        ...prevState.price,
        [priceKey]: option.price,
      },
    }));
  };

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
            selected={
              modelType.powerTrainId === option.id ||
              modelType.bodyTypeId === option.id ||
              modelType.wheelDriveId === option.id
            }
            onClick={() => {
              if (data.type === PICK_CARD.POWER_TRAIN)
                updateTrimState('powerTrainId', 'powerTrainId', 'powerTrainPrice', option);
              if (data.type === PICK_CARD.BODY_TYPE) {
                updateTrimState('bodyTypeId', 'bodyTypeId', 'bodyTypePrice', option);
              }
              if (data.type === PICK_CARD.WHEEL_DRIVE) {
                updateTrimState('wheelDriveId', 'wheelDriveId', 'wheelDrivePrice', option);
              }
            }}
          />
        ))}
      </S.SelectCard>
    </S.PickCard>
  );
}

export default PickCard;
