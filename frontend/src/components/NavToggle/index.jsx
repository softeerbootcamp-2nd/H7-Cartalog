import * as S from './style';

/**
 * Nav Toggle을 설정하는 컴포넌트
 * @param selected {bool} 현재 선택된 상태인지 설정하는 값
 * @returns
 */
function NavToggle({ selected }) {
  return <S.Bar className={selected ? 'selected' : null} />;
}

export default NavToggle;
