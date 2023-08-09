import * as S from './style';
import { useData, TotalPrice } from '../../../utils/Context';
import { TITLE } from './constants';
import PickTitle from '../../../components/PickTitle';
import PickCard from './PickCard';
import NextButton from '../../../components/NextButton';

function Pick() {
  const { modelType, price } = useData();

  const pickTitleProps = { mainTitle: TITLE };
  const nextButtonProps = {
    totalPrice: TotalPrice(price),
    estimateEvent: '',
    nextEvent: '',
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.SelectModel>
        {modelType.modelTypes.map((type) => (
          <PickCard key={type.type} />
        ))}
      </S.SelectModel>
      <NextButton {...nextButtonProps} />
    </S.Pick>
  );
}

export default Pick;
