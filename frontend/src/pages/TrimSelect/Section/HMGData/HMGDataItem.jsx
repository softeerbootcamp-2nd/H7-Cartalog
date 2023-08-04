import * as S from './HMGDataStyle';

function HMGDataItem({ data }) {
  return (
    <S.HMGDataItem>
      <S.Title>{data.title}</S.Title>
      <S.Divide />
      <S.Count>{data.count}회</S.Count>
      <S.Per>15,000km 당</S.Per>
    </S.HMGDataItem>
  );
}

export default HMGDataItem;
