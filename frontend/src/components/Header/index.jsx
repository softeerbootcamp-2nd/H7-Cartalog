import * as S from './style';
import Logo from './Logo';
import Nav from './Nav';
import Exit from './Exit';

function Header() {
  return (
    <S.Header>
      <S.Contents>
        <Logo />
        <Nav />
        <Exit />
      </S.Contents>
    </S.Header>
  );
}

export default Header;
