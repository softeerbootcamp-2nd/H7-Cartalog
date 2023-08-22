import { useData } from '../../../../utils/Context';
import HMGCard from '../../../../components/HMGCard';
import GrapArea from './GraphArea';

function PriceCard() {
  const data = useData();

  return (
    <HMGCard
      title={
        <>
          {data.trim.name}으로 완성된 모든 타입의
          <br />
          <span>견적 가격 분포</span>입니다.
        </>
      }
    >
      <GrapArea />
    </HMGCard>
  );
}

export default PriceCard;
