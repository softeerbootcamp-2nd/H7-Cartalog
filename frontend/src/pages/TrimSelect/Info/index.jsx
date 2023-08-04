import * as S from './style';
import Title from '../../../components/Title';
import HMGTag from '../../../components/HMGTag';
import HMGData from './HMGData';
import TrimImages from './TrimImages';

function Info() {
  return (
    <S.Info>
      <S.TrimText>
        <Title subTitle={'기본에 충실한 펠리세이드'} mainTitle={'Le Blanc'} />
        <S.HMG>
          <HMGTag type={'Tag1'} />
          <S.HMGInfo>
            해당 트림 포함된 옵션들의&nbsp;<S.Highlight>실활용 데이터</S.Highlight>에요.
          </S.HMGInfo>
          <HMGData
            dataList={[
              { title: '안전 하차 보조', count: 35 },
              { title: '후측방 충돌 경고', count: 45 },
              { title: '후방 교차 충돌방지 보조', count: 42 },
            ]}
          />
        </S.HMG>
      </S.TrimText>
      <TrimImages trim={'LeBlanc'} />
    </S.Info>
  );
}

export default Info;
