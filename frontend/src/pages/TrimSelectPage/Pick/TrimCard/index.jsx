import { useNavigate } from 'react-router-dom';
import { useData, StartAnimation } from '../../../../utils/Context';
import { PAGE, TRIM_CARD } from '../../constants';
import * as S from './style';
import Button from '../../../../components/Button';

function TrimCard({ name, description, price, defaultInfo, active, onClick }) {
  const navigate = useNavigate();
  const { setTrimState, trim, page, pagePath } = useData();

  const goToNextPage = () => {
    const nowPath = PAGE.find((type) => type.page === page).to;
    const nextPath = PAGE.find((type) => type.page === page + 1).to;
    StartAnimation(nowPath, nextPath, navigate, setTrimState, pagePath);
  };

  const updateState = () => {
    if (!trim.isDefault) {
      const {
        modelTypes: [powerTrainType, bodyType, wheelDriveType],
        exteriorColorCode,
        interiorColorCode,
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
          code: exteriorColorCode,
        },
        interiorColor: {
          ...prevState.interiorColor,
          code: interiorColorCode,
        },
        price: {
          ...prevState.price,
          trimPrice: price,
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
