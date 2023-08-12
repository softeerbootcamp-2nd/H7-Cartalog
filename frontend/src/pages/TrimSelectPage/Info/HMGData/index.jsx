import * as S from './style';

function HMGData({ data }) {
  return (
    <S.HMGData>
      {data?.map((item) => (
        <S.Item key={item.name}>
          <S.Title>{item.name}</S.Title>
          <S.Divide />
          <S.Count>{item.value}</S.Count>
          <S.Per>{item.measure}</S.Per>
        </S.Item>
      ))}
    </S.HMGData>
  );
}

export default HMGData;
