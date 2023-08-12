import * as S from './style';

function TypeSelector({ showDefault, setShowDefault }) {
  const handleClick = (value) => {
    if (showDefault === value) return;
    setShowDefault(value);
  };

  return (
    <S.TypeSelector>
      <S.SelectorItem
        className={showDefault ? null : 'selected'}
        onClick={() => handleClick(false)}
      >
        추가옵션
      </S.SelectorItem>
      <S.SelectorItem className={showDefault ? 'selected' : null} onClick={() => handleClick(true)}>
        기본옵션
      </S.SelectorItem>
    </S.TypeSelector>
  );
}

export default TypeSelector;
