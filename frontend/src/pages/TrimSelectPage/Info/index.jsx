import { INFO } from '../constants';
import * as S from './style';
import Title from '../../../components/Title';
import TrimImage from './TrimImage';
import HMGInfo from './HMGInfo';

function Info({ trim }) {
  const selectedTrim = trim.fetchData?.find((trimData) => trimData.id === trim.id);
  const titleProps = {
    type: INFO.TYPE,
    subTitle: selectedTrim?.description,
    mainTitle: selectedTrim?.name,
  };

  return (
    <S.Info>
      <S.TrimText>
        <Title {...titleProps} />
        <HMGInfo data={selectedTrim?.hmgData} />
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
