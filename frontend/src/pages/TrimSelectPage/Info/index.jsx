import * as S from './style';
import Title from '../../../components/Title';
import HMGTag from '../../../components/HMGTag';
import HMGData from './HMGData';
import TrimImage from './TrimImage';

const TYPE = 'dark';
const HMGTAG_TITLE = 'tag20';

const INFO = '해당 트림 포함된 옵션들의';
const INFO_DATA = '실활용 데이터';
const INFO_LAST = '에요.';

const MOCK_SELECTED_TRIM = {
  exterior:
    'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/le-blanc/exterior/A2B/001.png',
  interior:
    'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/le-blanc/interior/I49/img.png',
  wheel:
    'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/le-blanc/options/alconbreak_s.jpg',
};

function Info({ trimId, data }) {
  const selectedTrim = data?.find((trim) => trim.id === trimId);
  const titleProps = {
    type: TYPE,
    subTitle: selectedTrim?.description,
    mainTitle: selectedTrim?.name,
  };

  const hmgTagProps = { type: HMGTAG_TITLE };

  return (
    <S.Info>
      <S.TrimText>
        <Title {...titleProps} />
        <S.HMG>
          <HMGTag {...hmgTagProps} />
          <S.HMGInfo>
            {INFO}&nbsp;
            <S.Highlight>{INFO_DATA}</S.Highlight>
            {INFO_LAST}
          </S.HMGInfo>
          <HMGData dataList={selectedTrim?.hmgData} />
        </S.HMG>
      </S.TrimText>
      {/* <TrimImage data={selectedTrim} /> */}
      {/* FIXME: API 업데이트되면 위 코드로 바꾸기 */}
      <TrimImage data={MOCK_SELECTED_TRIM} />
    </S.Info>
  );
}

export default Info;
