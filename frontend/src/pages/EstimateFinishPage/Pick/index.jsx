import PropTypes from 'prop-types';
import * as S from './style';
import Button from '../../../components/Button';

const TYPE = 'buttonD';
const STATE = 'active';
const MAIN_TITLE = '다음';

function Pick({ nextPage }) {
  const buttonProps = {
    nextPage,
    type: TYPE,
    state: STATE,
    mainTitle: MAIN_TITLE,
  };

  return (
    <S.Pick>
      <Button {...buttonProps} />
    </S.Pick>
  );
}

Pick.propTypes = {
  nextPage: PropTypes.func.isRequired,
};

export default Pick;
