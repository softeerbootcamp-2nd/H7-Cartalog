import { useNavigate } from 'react-router-dom';
import { useData, StartAnimation } from '../../../utils/Context';
import { NAV } from '../constants';
import * as S from './style';
import NavToggle from '../../NavToggle';

function isLinkEnabled(item, modelType, exteriorColor, interiorColor, optionPicker) {
  switch (item.to) {
    case '/modelType':
      return modelType.isFetch;
    case '/exteriorColor':
      return exteriorColor.isFetch;
    case '/interiorColor':
      return interiorColor.isFetch;
    case '/optionPicker':
      return optionPicker.isFetch;
    default:
      return true;
  }
}

function Nav() {
  const navigate = useNavigate();
  const {
    setTrimState,
    page,
    modelType,
    exteriorColor,
    interiorColor,
    optionPicker,
    pageNum,
    pagePath,
  } = useData();

  return (
    <S.Nav>
      <S.Stage>
        {NAV.map((item) => (
          <S.Step key={item.label}>
            <S.Text>
              {isLinkEnabled(item, modelType, exteriorColor, interiorColor, optionPicker) ? (
                <S.Button
                  onClick={() => {
                    const nowPath = pageNum[page];
                    const nextPath = item.to;
                    StartAnimation(nowPath, nextPath, navigate, setTrimState, pagePath);
                  }}
                  className={item.page === page ? 'selected' : 'unSelected'}
                >
                  {item.label}
                </S.Button>
              ) : (
                <span>{item.label}</span>
              )}
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
