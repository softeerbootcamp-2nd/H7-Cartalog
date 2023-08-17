import { useData, NextPage } from '../../../../utils/Context';
import { TRIM_CARD } from '../../constants';
import * as S from './style';
import Button from '../../../../components/Button';

function TrimCard({ name, description, minPrice, maxPrice, defaultInfo, active, onClick }) {
  const { setTrimState, trim } = useData();

  const updateState = () => {
    if (!trim.isDefault) {
      const {
        modelTypes: [powerTrainType, bodyType, wheelDriveType],
        exteriorColor,
        interiorColor,
      } = defaultInfo;

      setTrimState((prevState) => ({
        ...prevState,
        budget: (minPrice + maxPrice) / 2,
        trim: {
          ...prevState.trim,
          minPrice,
          maxPrice,
        },
        modelType: {
          ...prevState.modelType,
          pickId: powerTrainType.option.id,
          powerTrainId: powerTrainType.option.id,
          bodyTypeId: bodyType.option.id,
          wheelDriveId: wheelDriveType.option.id,
        },
        exteriorColor: {
          ...prevState.exteriorColor,
          code: exteriorColor.code,
        },
        interiorColor: {
          ...prevState.interiorColor,
          code: interiorColor.code,
        },
        price: {
          ...prevState.price,
          trimPrice: minPrice,
          powerTrainPrice: powerTrainType.option.price,
          bodyTypePrice: bodyType.option.price,
          wheelDrivePrice: wheelDriveType.option.price,
        },
      }));
    }
  };

  const buttonProps = {
    type: TRIM_CARD.TYPE,
    state: TRIM_CARD.STATE,
    mainTitle: TRIM_CARD.MAIN_TITLE,
    event: () => {
      NextPage(setTrimState);
      updateState();
    },
  };

  return (
    <S.TrimCard className={active ? 'active' : null} onClick={onClick}>
      <S.Trim>
        <S.Description>{description}</S.Description>
        <S.Name>{name}</S.Name>
      </S.Trim>
      <S.Price>{minPrice.toLocaleString('ko-KR')}</S.Price>
      <S.SelectButton>
        <Button {...buttonProps} />
      </S.SelectButton>
    </S.TrimCard>
  );
}

export default TrimCard;
