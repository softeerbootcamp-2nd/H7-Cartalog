import DetailCard from './DetailCard';
import * as S from './style';

function Detail({ data }) {
  return (
    <S.Detail>
      <S.Title>상세견적</S.Title>
      <S.Content>
        {data.map((item) => (
          <DetailCard key={item.title} data={item} />
        ))}
      </S.Content>
    </S.Detail>
  );
}

export default Detail;
