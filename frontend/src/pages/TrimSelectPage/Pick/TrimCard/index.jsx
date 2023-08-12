import { useNavigate } from 'react-router-dom';
import { useData } from '../../../../utils/Context';
import { PAGE, TRIM_CARD } from '../../constants';
import * as S from './style';
import Button from '../../../../components/Button';

function TrimCard({ name, description, price, defaultInfo, active, onClick }) {
  const { setTrimState, page } = useData();
  const navigate = useNavigate();

  const goToNextPage = () => {
    const nextPage = PAGE.find((type) => type.page === page + 1);
    if (nextPage) {
      navigate(nextPage.to);
    }
  };

  const updateState = () => {
    const {
      modelTypes: [powerTrainType, bodyType, wheelDriveType],
      exteriorColorId,
      interiorColorId,
    } = defaultInfo;

    setTrimState((prevState) => ({
      ...prevState,
      modelType: {
        ...prevState.modelType,
        pickId: powerTrainType.option.id,
        powerTrainId: powerTrainType.option.id,
        bodyTypeId: bodyType.option.id,
        wheelDriveId: wheelDriveType.option.id,
      },
      exteriorColor: {
        ...prevState.exteriorColor,
        code: exteriorColorId,
      },
      interiorColor: {
        ...prevState.interiorColor,
        code: interiorColorId,
      },
      price: {
        ...prevState.price,
        trimPrice: price,
        powerTrainPrice: powerTrainType.option.price,
        bodyTypePrice: bodyType.option.price,
        wheelDrivePrice: wheelDriveType.option.price,
      },
    }));
  };

  const buttonProps = {
    type: TRIM_CARD.TYPE,
    state: TRIM_CARD.STATE,
    mainTitle: TRIM_CARD.MAIN_TITLE,
    event: () => {
      goToNextPage();
      updateState();
    },
  };

  return (
    <S.TrimCard className={active ? 'active' : null} onClick={onClick}>
      <S.Trim>
        <S.Description>{description}</S.Description>
        <S.Name>{name}</S.Name>
      </S.Trim>
      <S.Price>{price.toLocaleString('ko-KR')}</S.Price>
      <S.SelectButton>
        <Button {...buttonProps} />
      </S.SelectButton>
    </S.TrimCard>
  );
}

export default TrimCard;
