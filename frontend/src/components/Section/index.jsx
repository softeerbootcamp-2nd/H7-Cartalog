import * as S from './style';

const MODEL_TYPE = 'ModelType';
const INTERIOR_COLOR = 'InteriorColor';
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
  const SectionProps = { type };
  const isInteriorColor = type === INTERIOR_COLOR;
  const isAddOptionOrModelType = type === ADD_OPTION || type === MODEL_TYPE;

  return (
    <S.Section>
      <S.Background {...SectionProps}>
        {isInteriorColor && <S.BackgroundImage src={url} />}
        {isAddOptionOrModelType && (
          <>
            <S.ColorDiv {...SectionProps}>
              <S.Contents>{Info}</S.Contents>
            </S.ColorDiv>
            <S.ImageDiv src={url}></S.ImageDiv>
          </>
        )}
        {!isAddOptionOrModelType && <S.Contents {...SectionProps}>{Info}</S.Contents>}
      </S.Background>
      <S.Contents {...SectionProps}>{Pick}</S.Contents>
    </S.Section>
  );
}

export default Section;
