import * as S from './style';
import { HMGTag20 } from '../../../components/HMGTag';
import HMGData from './HMGData';
import Title from '../../../components/Title';
import TrimImages from './TrimImages';

function Info() {
  return (
    <S.Info>
      <S.Text>
        <Title
          subTitle={'기본에 충실한 펠리세이드'}
          mainTitle={'Le Blanc'}
          info={`높은 토크로 파워풀한 드라이빙이 가능하며,\n차급대비 연비 효율이 우수합니다`}
        />

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
      </S.Text>
      {/* 이미지 */}
      <TrimImages trim={'LeBlanc'} />
    </S.Info>
  );
}

export default Info;
