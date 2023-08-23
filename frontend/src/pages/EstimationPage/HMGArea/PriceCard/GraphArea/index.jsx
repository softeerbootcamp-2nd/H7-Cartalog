import { useData, TotalPrice } from '../../../../../utils/Context';
import * as S from './style';
import PriceGraph from '../../../../../components/PriceGraph';

function GrapArea() {
  const data = useData();

  return (
    <S.Graph>
      <PriceGraph
        min={data.trim.minPrice}
        max={data.trim.maxPrice}
        avg={data.estimation.averagePrice}
        value={TotalPrice(data.price)}
        width={314}
        height={117}
      />

      <S.PriceInfo>
        <S.Price className="min">
          최소
          <br />
          <span>{data.trim.minPrice.toLocaleString()} 원</span>
        </S.Price>
        <S.Price className="max">
          최대
          <br />
          <span>{data.trim.maxPrice.toLocaleString()} 원</span>
        </S.Price>
      </S.PriceInfo>
    </S.Graph>
  );
}

export default GrapArea;
