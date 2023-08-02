import { Link } from 'react-router-dom';
// import NavStyle from './NavStyle';
import * as S from './NavStyle';

function Nav() {
  return (
    <S.Nav>
      <S.Stage>
        <S.Step>
          <Link to="/">트림</Link>
        </S.Step>
        <S.Step>
          <Link to="/">타입</Link>
        </S.Step>
        <S.Step>
          <Link to="/">외장</Link>
        </S.Step>
        <S.Step>
          <Link to="/">내장</Link>
        </S.Step>
        <S.Step>
          <Link to="/">옵션</Link>
        </S.Step>
        <S.Step>
          <Link to="/">완료</Link>
        </S.Step>
      </S.Stage>
    </S.Nav>
  );
}

export default Nav;
