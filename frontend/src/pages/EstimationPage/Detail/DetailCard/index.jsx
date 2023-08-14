import { useState } from 'react';
import DetailItem from './DetailItem';
import * as S from './style';
import DetailTitle from './DetailTitle';

function DetailCard({ data }) {
  const [expanded, setExpanded] = useState(data?.expand || false);

  return (
    <S.DetailCard className={expanded ? 'expanded' : null}>
      <DetailTitle expanded={expanded} setExpanded={setExpanded} data={data} />
      <S.DetailContents $n={data?.data?.length}>
        {data?.data?.map((item) => (
          <DetailItem key={`${item.id}${item.name}`} data={item} />
        ))}
      </S.DetailContents>
    </S.DetailCard>
  );
}

export default DetailCard;
