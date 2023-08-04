import * as S from './style';
import Section from './Section';

function ModelType() {
  return (
    <S.ModelType>
      <S.Shadow>
        <S.Contents>
          <Section />
        </S.Contents>
      </S.Shadow>
      <S.Contents></S.Contents>
    </S.ModelType>
  );
}

export default ModelType;
