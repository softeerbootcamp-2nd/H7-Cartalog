import { DETAIL_ITEM } from '../../../constants';
import * as S from './style';

function DetailItem({ data }) {
  return (
    <S.DetailItem>
      <S.DetailImage src={data.image} alt={data.name} />
      <S.Description>
        <S.Area>
          {data.title && <div className="title">{data.title}</div>}
          <div>{data.name}</div>
        </S.Area>
        <S.Area>
          <button type="button">{DETAIL_ITEM.EDIT}</button>
          <div>
            +{data.price.toLocaleString()} {DETAIL_ITEM.WON}
          </div>
        </S.Area>
      </S.Description>
    </S.DetailItem>
  );
}

export default DetailItem;
