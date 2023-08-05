import * as S from './style';

function Pick({ nextPage }) {
  const buttonProps = {
    nextPage: nextPage,
    type: 'buttonD',
    state: 'active',
    mainTitle: '다음',
  };

  return (
    <S.Pick>
      <Button {...buttonProps}></Button>
    </S.Pick>
  );
}

export default Pick;
