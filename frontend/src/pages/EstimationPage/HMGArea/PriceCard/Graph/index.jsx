import { ReactComponent as GraphSvg } from './graph.svg';
import * as S from './style';

function Graph({ price }) {
  return (
    <S.Graph>
      <GraphSvg />
      <S.PriceInfo>
        <S.Price className="min">
          최소
          <br />
          <span>{price.min.toLocaleString()} 원</span>
        </S.Price>
        <S.Price className="max">
          최대
          <br />
          <span>{price.max.toLocaleString()} 원</span>
        </S.Price>
      </S.PriceInfo>
    </S.Graph>
  );
}

export default Graph;
