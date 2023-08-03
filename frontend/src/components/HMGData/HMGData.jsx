import HMGDataItem from './HMGDataItem';
import * as S from './HMGDataStyle';

function HMGData({ dataList }) {
  return (
    <S.HMGData id="asdf">
      {dataList.map((item, index) => (
        <HMGDataItem key={index} data={item} />
      ))}
    </S.HMGData>
  );
}

export default HMGData;
