import * as S from './style';

const PER = '15,000km 당';

/**
 * HMGData를 생성하는 컴포넌트
 * @param dataList {list} {[{ title: '', count: 35 }, {...}, {...}]}로 아이템은 3가지
 * @returns
 */
function HMGData({ dataList }) {
  return (
    <S.HMGData>
      {dataList?.map((item) => (
        <S.Item key={item.name + item.value}>
          <S.Title>{item.name}</S.Title>
          <S.Divide />
          <S.Count>{item.value}</S.Count>
          <S.Per>{PER}</S.Per>
        </S.Item>
      ))}
    </S.HMGData>
  );
}

export default HMGData;
