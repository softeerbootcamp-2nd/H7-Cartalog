import * as S from './style';
import Section from './Section';
import Select from './Select';

function TrimSelect({ nextPage }) {
  return (
    <S.TrimSelect>
      <S.Shadow>
        <S.Contents>
          <Section />
        </S.Contents>
      </S.Shadow>
      <S.Contents>
        <Select nextPage={nextPage} />
      </S.Contents>
    </S.TrimSelect>
  );
}

export default TrimSelect;
