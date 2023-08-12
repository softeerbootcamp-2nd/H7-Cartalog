import { useData } from '../../../utils/Context';
import { PICK } from '../constants';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import NextButton from '../../../components/NextButton';
import PickCard from './PickCard';
import HMGData from './HMGData';

function Pick() {
  const { modelType } = useData();
  const pickTitleProps = { mainTitle: PICK.TITLE };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.PickModel>
        {modelType.modelTypeFetch.map((data) => (
          <PickCard key={data.type} data={data} />
        ))}
      </S.PickModel>
      <S.Footer>
        <HMGData />
        <NextButton />
      </S.Footer>
    </S.Pick>
  );
}

export default Pick;
