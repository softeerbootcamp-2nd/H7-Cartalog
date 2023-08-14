import HMGCard from '../../../../components/HMGCard';
import Graph from './Graph';

function PriceCard({ data }) {
  return (
    <HMGCard
      title={
        <>
          {data.trim}으로 완성된 모든 타입의
          <br />
          <span>견적 가격 분포</span>입니다.
        </>
      }
    >
      <Graph price={data.price} />
    </HMGCard>
  );
}

export default PriceCard;
