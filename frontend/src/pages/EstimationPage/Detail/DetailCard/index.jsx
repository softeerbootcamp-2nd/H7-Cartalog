import { useEffect, useState } from 'react';
import * as S from './style';
import DetailItem from './DetailItem';
import DetailTitle from './DetailTitle';

function DetailCard({ data }) {
  const [expanded, setExpanded] = useState(data?.expand || false);

  useEffect(() => {
    setExpanded(data.expand);
  }, [data.expand]);

  return (
    <S.DetailCard className={expanded ? 'expanded' : null}>
      <DetailTitle expanded={expanded} setExpanded={setExpanded} data={data} type={data.type} />
      <S.DetailContents $n={data?.data?.length}>
        {data?.data?.map((item) => (
          <DetailItem key={`${item.id}${item.name}`} data={item} />
        ))}
      </S.DetailContents>
    </S.DetailCard>
  );
}

export default DetailCard;
