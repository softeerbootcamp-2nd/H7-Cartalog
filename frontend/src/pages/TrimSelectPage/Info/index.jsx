import * as S from './style';
import Title from '../../../components/Title';
import HMGTag from '../../../components/HMGTag';
import HMGData from './HMGData';
import TrimImage from './TrimImage';

const TYPE = 'dark';
const SUB_TITLE = '기본에 충실한 펠리세이드';
const MAIN_TITLE = 'Le Blanc';
const HMGTAG_TITLE = 'tag20';

const INFO = '해당 트림 포함된 옵션들의';
const INFO_DATA = '실활용 데이터';
const INFO_LAST = '에요.';

const HMG_TITLES = [
  { title: '안전 하차 보조', count: 35 },
  { title: '후측방 충돌 경고', count: 45 },
  { title: '후방 교차 충돌방지 보조', count: 42 },
];

const TRIM = 'LeBlanc';

function Info() {
  const titleProps = {
    type: TYPE,
    subTitle: SUB_TITLE,
    mainTitle: MAIN_TITLE,
  };

  const hmgTagProps = { type: HMGTAG_TITLE };
  const trimImageProps = { trim: TRIM };

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
          <HMGData dataList={HMG_TITLES} />
        </S.HMG>
      </S.TrimText>
      <TrimImage {...trimImageProps} />
    </S.Info>
  );
}

export default Info;
