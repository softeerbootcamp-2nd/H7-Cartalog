import HMGCard from '../../../../components/HMGCard';

function SimilarCard({ data }) {
  return (
    <HMGCard
      title={
        <>
          <span>내 견적과 비슷한 실제 출고 견적</span>들을
          <br />
          확인하고 비교해보세요.
        </>
      }
      description="유사 출고 견적이란, 내 견적과 해시태그 유사도가 높은 다른 사람들의 실제 출고 견적이에요."
    >
      {}
    </HMGCard>
  );
}

export default SimilarCard;
