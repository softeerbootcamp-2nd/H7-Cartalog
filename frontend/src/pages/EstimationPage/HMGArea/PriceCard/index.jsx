import HMGCard from '../../../../components/HMGCard';
import GraphArea from './GraphArea';

function PriceCard({ name, averagePrice }) {
  return (
    <HMGCard
      title={
        <>
          {name}으로 완성된 모든 타입의
          <br />
          <span>견적 가격 분포</span>입니다.
        </>
      }
    >
      <GraphArea averagePrice={averagePrice} />
    </HMGCard>
  );
}

export default PriceCard;
