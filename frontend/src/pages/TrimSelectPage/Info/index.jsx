import { useData } from '../../../utils/Context';
import { INFO } from '../constants';
import * as S from './style';
import Title from '../../../components/Title';
import HMGTag from '../../../components/HMGTag';
import HMGData from './HMGData';
import TrimImage from './TrimImage';

function Info() {
  const { trim } = useData();

  const selectedTrim = trim.trimFetch?.find((trimData) => trimData.id === trim.trimId);
  const hmgTagProps = { type: INFO.HMGTAG };
  const titleProps = {
    type: INFO.TYPE,
    subTitle: selectedTrim.description,
    mainTitle: selectedTrim.name,
  };

  return (
    <S.Info>
      <S.TrimText>
        <Title {...titleProps} />
        <S.HMG>
          <HMGTag {...hmgTagProps} />
          <S.HMGInfo>
            {INFO.HMGTAG_OPTION}&nbsp;
            <S.Highlight>{INFO.HMGTAG_DATA}</S.Highlight>
            {INFO.HMGTAG_LAST}
          </S.HMGInfo>
          <HMGData data={selectedTrim.hmgData} />
        </S.HMG>
      </S.TrimText>
      <TrimImage
        data={{
          exterior: selectedTrim.exteriorImageUrl,
          interior: selectedTrim.interiorImageUrl,
          wheel: selectedTrim.wheelImageUrl,
        }}
      />
    </S.Info>
  );
}

export default Info;
