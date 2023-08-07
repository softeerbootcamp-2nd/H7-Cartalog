import PropTypes from 'prop-types';
import * as S from './style';
import Title from '../../../components/Title';
import HMGTag from '../../../components/HMGTag';
import HMGData from './HMGData';

const TYPE = 'dark';
const SUB_TITLE = '파워트레인';
const MAIN_TITLE = '디젤 2.2';
const INFO = '높은 토크로 파워풀한 드라이빙이 가능하며,\n 차급대비 연비 효율이 우수합니다';

const HMGTAG_TYPE = 'tag20';

function Info({ imageUrl }) {
  const TitleProps = {
    type: TYPE,
    subTitle: SUB_TITLE,
    mainTitle: MAIN_TITLE,
    info: INFO,
  };

  const HMGTagProps = { type: HMGTAG_TYPE };

  return (
    <S.Info>
      <S.ModelText>
        <Title {...TitleProps} />
        <S.HMG>
          <HMGTag {...HMGTagProps} />
          <HMGData />
        </S.HMG>
      </S.ModelText>
      <S.ModelImage src={imageUrl} />
    </S.Info>
  );
}

Info.propTypes = {
  imageUrl: PropTypes.func.isRequired,
};

export default Info;
