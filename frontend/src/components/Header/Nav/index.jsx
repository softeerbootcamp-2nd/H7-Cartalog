import { Link } from 'react-router-dom';
import { useData } from '../../../utils/Context';
import { NAV } from '../constants';
import NavToggle from '../../NavToggle';
import * as S from './style';

function Nav() {
  const { page } = useData();

  return (
    <S.Nav>
      <S.Stage>
        {NAV.map((item) => (
          <S.Step key={item.label}>
            <S.Text className={item.page === page ? 'selected' : null}>
              <Link to={item.to}>{item.label}</Link>
            </S.Text>
            <S.Bar>
              <NavToggle selected={item.page === page} />
            </S.Bar>
          </S.Step>
        ))}
      </S.Stage>
    </S.Nav>
  );
}

export default Nav;
