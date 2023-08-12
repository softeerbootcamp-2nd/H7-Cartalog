import { useData } from '../../../../utils/Context';
import * as S from './style';
import TypeCard from '../../../../components/TypeCard';

function PickCard({ data }) {
  const { setTrimState, modelType } = useData();

  const updateTrimState = (idKey, optionKey, priceKey, option) => {
    if (modelType[idKey] === option.id) return;
    const updatedModelType = {
      ...modelType,
      pickId: option.id,
      [idKey]: option.id,
      [optionKey]: option,
    };

    const updatedPrice = {
      ...setTrimState.price,
      [priceKey]: option.price,
    };

    setTrimState((prevState) => ({
      ...prevState,
      modelType: updatedModelType,
      price: updatedPrice,
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
              if (data.type === modelType.powerTrainType)
                updateTrimState('powerTrainId', 'powerTrainOption', 'powerTrainPrice', option);
              if (data.type === modelType.bodyTypeType) {
                updateTrimState('bodyTypeId', 'bodyTypeOption', 'bodyTypePrice', option);
              }
              if (data.type === modelType.wheelDriveType) {
                updateTrimState('wheelDriveId', 'wheelDriveOption', 'wheelDrivePrice', option);
              }
            }}
          />
        ))}
      </S.SelectCard>
    </S.PickCard>
  );
}

export default PickCard;
