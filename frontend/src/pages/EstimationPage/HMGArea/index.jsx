import PriceCard from './PriceCard';
import SimilarCard from './SimilarCard';
import * as S from './style';

function HMGArea() {
  const MOCK_HMGDATA = {
    trim: '르블랑',
    price: {
      min: 43460000,
      max: 47650000,
      current: 45800000,
    },
    value: 2500,
    similar: [
      {
        id: 1,
        value: 3422,
      },
      {
        id: 2,
        value: 3200,
      },
      {
        id: 3,
        value: 3000,
      },
      {
        id: 4,
        value: 2800,
      },
    ],
  };

  return (
    <S.HMGArea>
      <PriceCard data={MOCK_HMGDATA} />
      <SimilarCard data={MOCK_HMGDATA} />
    </S.HMGArea>
  );
}

export default HMGArea;
