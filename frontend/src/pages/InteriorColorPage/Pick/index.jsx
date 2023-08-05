import * as S from './style';
import Button from '../../../components/Button';

const TYPE = 'buttonD';
const STATE = 'active';
const MAIN_TITLE = '다음';

function Pick({ nextPage }) {
  const buttonProps = {
    nextPage: nextPage,
    type: TYPE,
    state: STATE,
    mainTitle: MAIN_TITLE,
  };

  return (
    <S.Pick>
      <Button {...buttonProps}></Button>
    </S.Pick>
  );
}

export default Pick;
