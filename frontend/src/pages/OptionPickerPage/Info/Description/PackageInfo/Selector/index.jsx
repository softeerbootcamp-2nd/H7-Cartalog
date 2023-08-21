import { useLayoutEffect, useRef, useState } from 'react';
import * as S from './style';
import { ReactComponent as LeftArrow } from '../../../../../../../assets/icons/arrow_left.svg';
import { ReactComponent as RightArrow } from '../../../../../../../assets/icons/arrow_right.svg';

function Selector({ options, selected, setSelected }) {
  const barRef = useRef();
  const [rightClassName, setRightClassName] = useState('');
  const [leftClassName, setLeftClassName] = useState('');
  const moveBar = (target) => {
    barRef.current.style.transform = `translateX(${target.offsetLeft}px) scaleX(${
      target.offsetWidth / target.offsetParent.offsetWidth
    })`;
  };
  const handleSelect = ({ target }, name) => {
    if (selected === name) return;
    setSelected(name);
    moveBar(target);
  };
  const setMoveAvailability = (targetScrollLeft) => {
    const { scrollLeft, scrollWidth, offsetWidth } = barRef.current.offsetParent;
    const scrollLeftToCheck = targetScrollLeft || scrollLeft;

    setRightClassName(scrollLeftToCheck + offsetWidth < scrollWidth ? 'active' : '');
    setLeftClassName(scrollLeftToCheck > 0 ? 'active' : '');
  };
  const handlePageMove = (moveNext) => {
    const target = barRef.current.offsetParent;
    const { scrollLeft, offsetWidth } = target;
    const targetScrollLeft = moveNext ? scrollLeft + offsetWidth : scrollLeft - offsetWidth;

    target.scrollTo({
      left: targetScrollLeft,
      behavior: 'smooth',
    });
    setMoveAvailability(targetScrollLeft);
  };

  useLayoutEffect(() => {
    const target = barRef.current.offsetParent.querySelector('.selected');

    moveBar(target);
    setMoveAvailability();
  }, []);

  return (
    <S.Selector>
      <S.ArrowButton className={leftClassName} onClick={() => handlePageMove(false)}>
        <LeftArrow />
      </S.ArrowButton>
      <S.SelectorList>
        <S.Bar ref={barRef} />
        {options.map((option) => (
          <S.SelectorItem
            key={option.name}
            className={selected === option.name ? 'selected' : ''}
            onClick={(event) => handleSelect(event, option.name)}
          >
            {option.name}
          </S.SelectorItem>
        ))}
      </S.SelectorList>
      <S.ArrowButton className={rightClassName} onClick={() => handlePageMove(true)}>
        <RightArrow />
      </S.ArrowButton>
    </S.Selector>
  );
}

export default Selector;
