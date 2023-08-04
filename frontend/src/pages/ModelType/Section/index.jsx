import * as S from './style';
// import { HMGTag20 } from '../../../components/HMGTag';
import HMGData from './HMGData';

function Section() {
  return (
    <S.Section>
      <S.Contents>
        {/* 타이틀 */}
        <S.Title>
          <S.SubTitle>파워트레인</S.SubTitle>
          <S.MainTitle>디젤 2.2</S.MainTitle>
          <S.InfoTitle>
            높은 토크와 파워풀한 드라이빙이 가능하며, 차급대비 연비 효율이 우수합니다
          </S.InfoTitle>
        </S.Title>

        {/* 현대 데이터 */}
        <S.Data>
          {/* <HMGTag20 /> */}
          <HMGData />
        </S.Data>
      </S.Contents>
      {/* 이미지 */}
    </S.Section>
  );
}

export default Section;
