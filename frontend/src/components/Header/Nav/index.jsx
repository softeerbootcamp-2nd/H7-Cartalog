import { useData, SelectPage } from '../../../utils/Context';
import { NAV } from '../constants';
import * as S from './style';
import NavToggle from '../../NavToggle';

function isLinkEnabled(item, modelType, exteriorColor, interiorColor, optionPicker, estimation) {
  switch (item.label) {
    case '타입':
      return modelType.isFetch;
    case '외장':
      return exteriorColor.isFetch;
    case '내장':
      return interiorColor.isFetch;
    case '옵션':
      return optionPicker.isFetch;
    case '완료':
      return estimation.isFetch;
    default:
      return true;
  }
}

function Nav() {
  const { setTrimState, page, modelType, exteriorColor, interiorColor, optionPicker, estimation } =
    useData();

  return (
    <S.Nav>
      <S.Stage>
        {NAV.map((item) => (
          <S.Step key={item.label}>
            <S.Text>
              {isLinkEnabled(
                item,
                modelType,
                exteriorColor,
                interiorColor,
                optionPicker,
                estimation,
              ) ? (
                <S.Button
                  onClick={() => SelectPage(setTrimState, item.page)}
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
