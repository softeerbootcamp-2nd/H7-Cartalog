import * as S from './style';
import Button from '../../../components/Button';

function Pick({ nextPage }) {
  const buttonProps = {
    nextPage: nextPage,
    type: 'buttonD',
    state: 'active',
    mainTitle: '다음',
  };

  return (
    <S.Pick>
      {/* 수정필요 */}
      <Button {...buttonProps}></Button>
    </S.Pick>
  );
}

export default Pick;
