import * as S from './style';
import Title from '../../../components/Title';
import HMGTag from '../../../components/HMGTag';

import HMGData from './HMGData';

function Info() {
  return (
    <S.Info>
      <S.ModelText>
        <Title
          type={'dark'}
          subTitle={'파워브레인'}
          mainTitle={'디젤 2.2'}
          info={'높은 토크로 파워풀한 드라이빙이 가능하며,\n 차급대비 연비 효율이 우수합니다'}
        />
        <S.HMG>
          <HMGTag type={'Tag1'} />
          {/* 개발 진행 중 */}
          <HMGData />
        </S.HMG>
      </S.ModelText>
    </S.Info>
  );
}

export default Info;
