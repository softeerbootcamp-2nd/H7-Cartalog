import PriceCard from './PriceCard';
import SimilarCard from './SimilarCard';
import * as S from './style';

function HMGArea({ data }) {
  return (
    <S.HMGArea>
      <PriceCard data={data} />
      <SimilarCard data={data} />
    </S.HMGArea>
  );
}

export default HMGArea;
