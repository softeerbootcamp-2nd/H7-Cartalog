import { DETAIL } from '../constants';
import * as S from './style';
import DetailCard from './DetailCard';

function Detail({ data }) {
  return (
    <S.Detail>
      <S.Title>{DETAIL.TITLE}</S.Title>
      <S.Content>
        {data.map((item) => (
          <DetailCard key={item.title} data={item} />
        ))}
      </S.Content>
    </S.Detail>
  );
}

export default Detail;
