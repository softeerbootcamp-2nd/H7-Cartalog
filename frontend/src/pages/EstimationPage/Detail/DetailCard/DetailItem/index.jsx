import * as S from './style';

function DetailItem({ data }) {
  return (
    <S.DetailItem>
      <img src={data.image} alt={data.name} />
      <S.Description>
        <S.Area>
          <div className="title">{data.title}</div>
          <div>{data.name}</div>
        </S.Area>
        <S.Area>
          <button type="button">수정하기</button>
          <div>+{data.price.toLocaleString()} 원</div>
        </S.Area>
      </S.Description>
    </S.DetailItem>
  );
}

export default DetailItem;
