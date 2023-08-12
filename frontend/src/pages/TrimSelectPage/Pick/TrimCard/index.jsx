import { useNavigate } from 'react-router-dom';
import { useData } from '../../../../utils/Context';
import { PAGE, TRIM_CARD } from '../../constants';
import * as S from './style';
import Button from '../../../../components/Button';

function TrimCard({ name, description, price, defaultInfo, active, onClick }) {
  const { setTrimState, page } = useData();
  const navigate = useNavigate();

  const buttonProps = {
    type: TRIM_CARD.TYPE,
    state: TRIM_CARD.STATE,
    mainTitle: TRIM_CARD.MAIN_TITLE,
    event: () => {
      navigate(PAGE.find((type) => type.page === page + 1).to);
      setTrimState((prevState) => ({
        ...prevState,
        modelType: {
          ...prevState.modelType,
          powerTrainId: defaultInfo.modelTypes[0].option.id,
          bodyTypeId: defaultInfo.modelTypes[1].option.id,
          wheelDriveId: defaultInfo.modelTypes[2].option.id,
          powerTrainOption: defaultInfo.modelTypes[0].option,
          bodyTypeOption: defaultInfo.modelTypes[1].option,
          wheelDriveOption: defaultInfo.modelTypes[2].option,
        },
        exteriorColor: {
          ...prevState.exteriorColor,
          exteriorColorId: defaultInfo.exteriorColorId,
        },
        interiorColor: {
          ...prevState.interiorColor,
          interiorColorId: defaultInfo.interiorColorId,
        },
        price: {
          ...prevState.price,
          trimPrice: price,
        },
      }));
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
