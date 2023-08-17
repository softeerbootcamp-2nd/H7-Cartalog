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
          name,
          minPrice,
          maxPrice,
        },
        modelType: {
          ...prevState.modelType,
          pickId: powerTrainType.option.id,
          powerTrainName: powerTrainType.option.name,
          bodyTypeName: bodyType.option.name,
          wheelDriveName: wheelDriveType.option.name,
          powerTrainId: powerTrainType.option.id,
          bodyTypeId: bodyType.option.id,
          wheelDriveId: wheelDriveType.option.id,
        },
        exteriorColor: {
          ...prevState.exteriorColor,
          code: exteriorColor.code,
          name: exteriorColor.name,
        },
        interiorColor: {
          ...prevState.interiorColor,
          code: interiorColor.code,
          name: interiorColor.name,
        },
        price: {
          ...prevState.price,
          trimPrice: minPrice,
          powerTrainPrice: powerTrainType.option.price,
          bodyTypePrice: bodyType.option.price,
          wheelDrivePrice: wheelDriveType.option.price,
          exteriorColorPrice: exteriorColor.price,
          interiorColorPrice: interiorColor.price,
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
