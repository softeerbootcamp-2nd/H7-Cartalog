import * as S from './style';

function Pick({ nextPage }) {
  return (
    <S.Pick>
      {/* 수정필요 */}
      <S.SelectButton onClick={nextPage}>선택하기</S.SelectButton>
    </S.Pick>
  );
}

export default Pick;
