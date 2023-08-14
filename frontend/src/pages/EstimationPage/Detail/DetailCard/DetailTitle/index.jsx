import * as S from './style';
import { ReactComponent as ArrowDown } from '../../../../../../assets/icons/arrow_down_24.svg';

function DetailTitle({ expanded, setExpanded, data }) {
  const price = data?.data
    ?.reduce((acc, cur) => {
      return acc + cur.price ?? 0;
    }, 0)
    .toLocaleString();

  return (
    <S.DetailTitle>
      <S.Title>{data?.title}</S.Title>
      <S.Area>
        <S.Price>{`+${price} 원`}</S.Price>
        <button type="button" onClick={() => setExpanded((prev) => !prev)}>
          <ArrowDown className={expanded ? 'open' : null} />
        </button>
      </S.Area>
    </S.DetailTitle>
  );
}

export default DetailTitle;
