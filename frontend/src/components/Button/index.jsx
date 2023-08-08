import * as S from './style';

/**
 * Button을 보여주는 컴포넌트
 * @param type {string} buttonA || buttonB || buttonC || buttonD
 * @param state {string} active || hover || click || inactive
 * @param subTitle {string} 서브 타이틀 작성
 * @param mainTitle {string} 메인 타이틀 작성
 * @returns
 */
function Button({ type, state, subTitle, mainTitle }) {
  const ButtonProps = {
    type,
    $state: state,
  };

  return (
    <S.Button {...ButtonProps}>
      <S.SubTitle>{subTitle}</S.SubTitle>
      <S.MainTitle {...ButtonProps}>{mainTitle}</S.MainTitle>
    </S.Button>
  );
}

export default Button;
