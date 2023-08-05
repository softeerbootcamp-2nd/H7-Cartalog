import * as S from './style';

/**
 * HMGTag를 생성하는 컴포넌트
 * @param type {string} tag20 || tag32
 * @returns
 */
function HMGTag({ type }) {
  const HMGTagProps = {
    type: type,
  };

  return (
    <S.HMGTag {...HMGTagProps}>
      <S.Text>HMG Data</S.Text>
    </S.HMGTag>
  );
}

export default HMGTag;
