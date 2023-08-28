import HMGData from '../../../../components/HMGData';
import HMGTag from '../../../../components/HMGTag';
import { INFO } from '../../constants';
import * as S from './style';

function HMGInfo({ data }) {
  return (
    <S.HMGInfo>
      <HMGTag type={INFO.HMGTAG} />
      <S.HMGInfoData>
        {INFO.HMGTAG_OPTION}&nbsp;
        <S.Highlight>{INFO.HMGTAG_DATA}</S.Highlight>
        {INFO.HMGTAG_LAST}
      </S.HMGInfoData>
      <HMGData data={data} />
    </S.HMGInfo>
  );
}

export default HMGInfo;
