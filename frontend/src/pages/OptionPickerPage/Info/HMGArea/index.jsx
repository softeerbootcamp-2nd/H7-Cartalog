import HMGData from '../../../../components/HMGData';
import HMGTag from '../../../../components/HMGTag';
import * as S from './style';

function HMGArea({ data }) {
  return (
    <S.HMGArea>
      <HMGTag type="tag20" />
      <HMGData data={data} type="option" />
    </S.HMGArea>
  );
}

export default HMGArea;
