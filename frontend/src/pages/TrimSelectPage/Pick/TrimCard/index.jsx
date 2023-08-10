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
          powerTrainId: defaultInfo.modelTypes.map((model) => model.option.id[0]),
          bodyTypeId: defaultInfo.modelTypes.map((model) => model.option.id[1]),
          wheelDriveId: defaultInfo.modelTypes.map((model) => model.option.id[2]),
        },
        exteriorColor: {
          exteriorColorId: defaultInfo.exteriorColorId,
        },
        interiorColor: {
          interiorColorId: defaultInfo.interiorColorId,
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
