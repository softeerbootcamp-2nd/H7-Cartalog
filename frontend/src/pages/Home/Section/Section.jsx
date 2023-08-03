import * as S from './SectionStyle';
import HMGTag20 from '../../../components/HMGTag/HMGTag20';
import HMGData from '../../../components/HMGData/HMGData';
import TrimImages from './TrimImages/TrimImages';

function Section() {
  return (
    <S.Section>
      <S.Contents>
        {/* 타이틀 */}
        <S.Title>
          <S.SubTitle>기본에 충실한 펠리세이드</S.SubTitle>
          <S.MainTitle>Le Blanc</S.MainTitle>
        </S.Title>

        {/* 현대 데이터 */}
        <S.Data>
          <HMGTag20 />
          <S.Info>
            해당 트림 포함된 옵션들의&nbsp;<S.MainInfo>실활용 데이터</S.MainInfo>에요.
          </S.Info>
          <HMGData
            dataList={[
              { title: '안전 하차 보조', count: 35 },
              { title: '후측방 충돌 경고', count: 42 },
              { title: '후방 교차 충돌방지 보조', count: 42 },
            ]}
          />
        </S.Data>
      </S.Contents>
      {/* 이미지 */}
      <TrimImages trim={'LeBlanc'} />
    </S.Section>
  );
}

export default Section;
