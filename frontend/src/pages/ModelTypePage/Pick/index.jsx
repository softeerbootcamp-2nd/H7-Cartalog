import * as S from './style';
import { useData, TotalPrice } from '../../../utils/Context';
import { TITLE } from './constants';
import PickTitle from '../../../components/PickTitle';
import NextButton from '../../../components/NextButton';
import PickCard from './PickCard';
import HMGData from './HMGData';

// ! API 연결 후 수정 필요
function Pick() {
  const { modelType, price } = useData();
  console.log(modelType);

  const pickTitleProps = { mainTitle: TITLE };
  const nextButtonProps = {
    totalPrice: TotalPrice(price),
    estimateEvent: '',
    nextEvent: '',
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.PickModel>
        {modelType.modelTypes.map((type) => (
          <PickCard key={type.type} />
        ))}
      </S.PickModel>
      <S.Footer>
        <HMGData />
        <NextButton {...nextButtonProps} />
      </S.Footer>
    </S.Pick>
  );
}

export default Pick;
