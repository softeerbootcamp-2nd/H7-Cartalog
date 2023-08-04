import HMGDataItem from './HMGDataItem';
import * as S from './style';

function HMGData({ dataList }) {
  return (
    <S.HMGData>
      {dataList.map((item, index) => (
        <HMGDataItem key={index} data={item} />
      ))}
    </S.HMGData>
  );
}

export default HMGData;
