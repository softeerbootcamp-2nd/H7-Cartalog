import HMGCard from '../../../../components/HMGCard';

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
      {}
    </HMGCard>
  );
}

export default PriceCard;
