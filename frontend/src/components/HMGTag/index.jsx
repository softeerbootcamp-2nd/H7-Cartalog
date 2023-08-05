import * as S from './style';

/**
 * HMGTag를 생성하는 컴포넌트
 * @param type {string} Tag1 || Tag2
 * @returns
 */
function HMGTag({ type }) {
  return (
    <S.HMGTag type={type}>
      <S.Text>HMG Data</S.Text>
    </S.HMGTag>
  );
}

export default HMGTag;
