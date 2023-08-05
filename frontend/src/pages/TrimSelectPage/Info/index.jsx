import * as S from './style';
import Title from '../../../components/Title';
import HMGTag from '../../../components/HMGTag';
import HMGData from './HMGData';
import TrimImage from './TrimImage';

const TYPE = 'dark';
const SUB_TITLE = '기본에 충실한 펠리세이드';
const MAIN_TITLE = 'Le Blanc';
const HMGTAG_TITLE = 'tag20';

const INFO = `해당 트림 포함된 옵션들의`;
const INFO_DATA = '실활용 데이터';
const INFO_LAST = '에요.';

const TITLE = ['안전 하차 보조', 35];
const TITLE_2 = ['후측방 충돌 경고', 45];
const TITLE_3 = ['후방 교차 충돌방지 보조', 42];

const TRIM = 'LeBlanc';

function Info() {
  const TitleProps = {
    type: TYPE,
    subTitle: SUB_TITLE,
    mainTitle: MAIN_TITLE,
  };

  const HMGTagProps = {
    type: HMGTAG_TITLE,
  };

  const TrimImageProps = {
    trim: TRIM,
  };

  return (
    <S.Info>
      <S.TrimText>
        <Title {...TitleProps} />
        <S.HMG>
          <HMGTag {...HMGTagProps} />
          <S.HMGInfo>
            {INFO}&nbsp;
            <S.Highlight>{INFO_DATA}</S.Highlight>
            {INFO_LAST}
          </S.HMGInfo>
          <HMGData
            dataList={[
              { title: TITLE[0], count: TITLE[1] },
              { title: TITLE_2[0], count: TITLE_2[1] },
              { title: TITLE_3[0], count: TITLE_3[1] },
            ]}
          />
        </S.HMG>
      </S.TrimText>
      <TrimImage {...TrimImageProps} />
    </S.Info>
  );
}

export default Info;
