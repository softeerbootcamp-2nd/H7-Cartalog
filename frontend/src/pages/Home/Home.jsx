import Header from '../../components/Header/Header';
import * as S from './HomeStyle';

import Section from './Section/Section';
import Select from './Select/Select';

function Home({ nextPage }) {
  return (
    <S.Home>
      <Header />
      <S.Shadow>
        <S.Contents>
          <Section />
        </S.Contents>
      </S.Shadow>
      <S.Contents>
        <Select nextPage={nextPage} />
      </S.Contents>
    </S.Home>
  );
}

export default Home;
