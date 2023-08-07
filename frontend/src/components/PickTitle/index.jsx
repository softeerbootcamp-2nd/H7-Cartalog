import PropTypes from 'prop-types';
import * as S from './style';

/**
 * SelectTitle을 보여주는 컴포넌트
 * @param mainTitle {string} 메인 타이틀 작성
 * @returns
 */
function PickTitle({ mainTitle }) {
  return (
    <S.PickTitle>
      <S.MainTitle>{mainTitle}</S.MainTitle>
    </S.PickTitle>
  );
}

PickTitle.propTypes = {
  mainTitle: PropTypes.string.isRequired,
};

export default PickTitle;
