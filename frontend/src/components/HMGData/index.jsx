import * as S from './style';

function HMGData({ data, type }) {
  return (
    <S.HMGData $type={type}>
      {data?.map((item) => (
        <S.Item key={item.name} $type={type}>
          <S.Title $type={type}>{item.name}</S.Title>
          <S.Divide />
          <S.Count>{item.value}</S.Count>
          <S.Per>{item.measure}</S.Per>
        </S.Item>
      ))}
    </S.HMGData>
  );
}

export default HMGData;
