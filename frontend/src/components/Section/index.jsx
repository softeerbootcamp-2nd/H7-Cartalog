import * as S from './style';

const ADD_OPTION = 'AddOption';

/**
 * Section 구역을 분리하는 컴포넌트
 * @param type {string} TrimSelect || ModelType || ExteriorColor || InteriorColor || AddOption
 * @param url {string} InteriorColor, AddOption에 필요한 이미지 URL
 * @param Info {Comment} 'Info' 구역에 안에 넣을 컴포넌트
 * @param Pick {Comment} 'Pick' 구역에 안에 넣을 컴포넌트
 * @returns
 */
function Section({ type, url, Info, Pick }) {
  const SectionProps = {
    type: type,
    $url: url,
  };

  if (type === ADD_OPTION) {
    return (
      <S.Section>
        <S.Background {...SectionProps}>
          <S.ColorDiv>
            <S.Contents>{Info}</S.Contents>
          </S.ColorDiv>
          <S.ImageDiv {...SectionProps}></S.ImageDiv>
        </S.Background>
        <S.Contents>{Pick}</S.Contents>
      </S.Section>
    );
  } else {
    return (
      <S.Section>
        <S.Background {...SectionProps}>
          <S.Contents>{Info}</S.Contents>
        </S.Background>
        <S.Contents>{Pick}</S.Contents>
      </S.Section>
    );
  }
}

export default Section;
