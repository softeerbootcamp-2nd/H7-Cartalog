import * as S from './style';

/**
 * Title을 보여주는 컴포넌트
 * @param type {string} light/dark 모드 설정
 * @param subTitle {string} 서브 타이틀 작성
 * @param mainTitle {string} 메인 타이틀 작성
 * @param info {string} 설명 작성
 * @returns
 */
function Title({ type, subTitle, mainTitle, info }) {
  return (
    <S.Title>
      <S.SubTitle type={type}>{subTitle}</S.SubTitle>
      <S.MainTitle type={type}>{mainTitle}</S.MainTitle>
      <S.Info type={type}>{info}</S.Info>
    </S.Title>
  );
}

export default Title;
