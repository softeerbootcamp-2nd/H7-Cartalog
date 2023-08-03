import * as S from './HomeStyle';

import Section from './Section/Section';
import Select from './Select/Select';

function Home() {
  return (
    <S.Home>
      <S.Shadow>
        <S.Contents>
          <Section />
        </S.Contents>
      </S.Shadow>
      <S.Contents>
        <Select />
      </S.Contents>
    </S.Home>
  );
}

export default Home;
