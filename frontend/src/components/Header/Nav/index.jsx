import { Link } from 'react-router-dom';
import * as S from './style';

function Nav() {
  return (
    <S.Nav>
      <S.Stage>
        <S.Step>
          <Link to="/">트림</Link>
        </S.Step>
        <S.Step>
          <Link to="/modelType">타입</Link>
        </S.Step>
        <S.Step>
          <Link to="/exteriorColor">외장</Link>
        </S.Step>
        <S.Step>
          <Link to="/interiorColor">내장</Link>
        </S.Step>
        <S.Step>
          <Link to="/addOptions">옵션</Link>
        </S.Step>
        <S.Step>
          <Link to="/estimateFinish">완료</Link>
        </S.Step>
      </S.Stage>
    </S.Nav>
  );
}

export default Nav;
