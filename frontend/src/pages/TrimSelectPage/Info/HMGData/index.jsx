import * as S from './style';

/**
 * HMGData를 생성하는 컴포넌트
 * @param dataList {list} {[{ title: '', count: 35 }, {...}, {...}]}로 아이템은 3가지
 * @returns
 */
function HMGData({ dataList }) {
  return (
    <S.HMGData>
      {dataList.map((item, index) => (
        <S.Item key={index}>
          <S.Title>{item.title}</S.Title>
          <S.Divide />
          <S.Count>{item.count}회</S.Count>
          <S.Per>15,000km 당</S.Per>
        </S.Item>
      ))}
    </S.HMGData>
  );
}

export default HMGData;
