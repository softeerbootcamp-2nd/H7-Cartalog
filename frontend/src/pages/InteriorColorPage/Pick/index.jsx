import * as S from './style';
import Button from '../../../components/Button';
import PickTitle from '../../../components/PickTitle';

const TYPE = 'buttonD';
const STATE = 'active';
const MAIN_TITLE = '다음';
const PICK_MAIN_TITLE = '내장 색상을 선택해주세요.';

function Pick({ nextPage }) {
  const buttonProps = {
    nextPage: nextPage,
    type: TYPE,
    state: STATE,
    mainTitle: MAIN_TITLE,
  };

  const pickTitleProps = { mainTitle: PICK_MAIN_TITLE };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <Button {...buttonProps}></Button>
    </S.Pick>
  );
}

export default Pick;
