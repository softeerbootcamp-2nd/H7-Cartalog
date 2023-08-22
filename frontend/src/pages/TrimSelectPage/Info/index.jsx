import { useData } from '../../../utils/Context';
import { INFO } from '../constants';
import * as S from './style';
import Title from '../../../components/Title';
import HMGTag from '../../../components/HMGTag';
import TrimImage from './TrimImage';
import HMGData from '../../../components/HMGData';

function Info() {
  const { trim } = useData();
  const selectedTrim = trim.fetchData?.find((trimData) => trimData.id === trim.id);
  const hmgTagProps = { type: INFO.HMGTAG };
  const titleProps = {
    type: INFO.TYPE,
    subTitle: selectedTrim?.description,
    mainTitle: selectedTrim?.name,
  };

  return (
    <S.Info>
      <S.TrimText>
        <Title {...titleProps} />
        <S.HMG>
          <HMGTag {...hmgTagProps} />
          <S.HMGInfo>
            {INFO.HMGTAG_OPTION} <br />
            <S.Highlight>{INFO.HMGTAG_DATA}</S.Highlight>
            {INFO.HMGTAG_LAST}
          </S.HMGInfo>
          <HMGData data={selectedTrim?.hmgData} />
        </S.HMG>
      </S.TrimText>
      <TrimImage
        data={{
          exterior: selectedTrim?.exteriorImageUrl,
          interior: selectedTrim?.interiorImageUrl,
          wheel: selectedTrim?.wheelImageUrl,
        }}
      />
    </S.Info>
  );
}

export default Info;
