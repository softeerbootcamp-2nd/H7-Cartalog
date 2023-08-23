import { useData } from '../../../utils/Context';
import * as S from './style';
import PriceCard from './PriceCard';

import SimilarCard from './SimilarCard';

function HMGArea() {
  const data = useData();

  return (
    <S.HMGArea>
      <PriceCard />
      {data.estimation.similarEstimateCounts.length !== 0 && <SimilarCard />}
    </S.HMGArea>
  );
}

export default HMGArea;
