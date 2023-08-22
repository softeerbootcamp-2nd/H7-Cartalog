import { ReactComponent as GraphSvg } from './graph.svg';
import { ReactComponent as PointSvg } from './point.svg';
import { useData } from '../../../../../utils/Context';
import * as S from './style';
// import GraphSvg from '../GraphSvg';

function GrapArea() {
  // const values = [38960000, 39978000, 40056000, 48990000, 50123000, 54012300, 56770000];

  const data = useData();

  return (
    <S.Graph>
      <GraphSvg />
      <S.Point>
        <PointSvg />
        <S.PointText>내 견적</S.PointText>
      </S.Point>

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
