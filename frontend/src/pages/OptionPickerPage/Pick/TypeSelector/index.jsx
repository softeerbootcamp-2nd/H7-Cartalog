import { useLayoutEffect, useRef } from 'react';
import * as S from './style';

const TYPE_DATA = [
  {
    name: '추가옵션',
    value: false,
  },
  {
    name: '기본옵션',
    value: true,
  },
];

function TypeSelector({ showDefault, setShowDefault }) {
  const barRef = useRef();
  const moveBar = (target) => {
    barRef.current.style.transform = `translateX(${
      target.offsetLeft + (target.offsetWidth / 2 - 9)
    }px)`;
  };
  const handleClick = ({ target }, value) => {
    if (showDefault === value) return;
    setShowDefault(value);
    moveBar(target);
  };

  useLayoutEffect(() => {
    const target = barRef.current.offsetParent.querySelector('.selected');

    moveBar(target);
  }, []);

  return (
    <S.TypeSelector>
      <S.Bar ref={barRef} />
      {TYPE_DATA.map(({ name, value }) => {
        if (showDefault === value) {
          // barRef.current.style.transform = `translateX(${event.target.offsetLeft}px)`;
        }
        return (
          <S.SelectorItem
            key={name}
            className={showDefault === value ? 'selected' : null}
            onClick={(event) => handleClick(event, value)}
          >
            {name}
          </S.SelectorItem>
        );
      })}
    </S.TypeSelector>
  );
}

export default TypeSelector;
