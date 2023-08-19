import { ReactComponent as ArrowDown } from '../../../../../../assets/icons/arrow_down_24.svg';
import { TYPE } from '../../../constants';
import * as S from './style';

function DetailTitle({ expanded, setExpanded, data, type }) {
  const price = (data?.data ?? [])
    .reduce((acc, cur) => {
      return acc + (cur.price ?? 0);
    }, 0)
    .toLocaleString();

  return (
    <S.DetailTitle>
      <S.Title>{data?.title}</S.Title>
      <S.Area>
        <S.Price>
          {type === TYPE.PLUS && `+${price} 원`}
          {type === TYPE.MINUS && `-${price} 원`}
          {type === TYPE.PAYMENT && TYPE.PAYMENT_TEXT}
          {type === TYPE.TAX && TYPE.TAX_MENT}
        </S.Price>
        <button type="button" onClick={() => setExpanded((prev) => !prev)}>
          <ArrowDown className={expanded ? 'open' : null} />
        </button>
      </S.Area>
    </S.DetailTitle>
  );
}

export default DetailTitle;
