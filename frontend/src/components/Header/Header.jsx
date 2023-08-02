import * as S from './HeaderStyle';
import Logo from './Logo/Logo';
import Nav from './Nav/Nav';
import Exit from './Exit/Exit';

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
