import { useState } from 'react';
import * as S from './style';
import Description from './Description';
import { ReactComponent as ArrowDown } from '../../../assets/icons/arrow_down.svg';
import Slider from './Slider';
import SliderMark from './SliderMark';

const TITLE = '예산범위';

const PriceStaticBar = ({ min, max, price }) => {
  const [expanded, setExpanded] = useState(false);
  const [budget, setBudget] = useState((min + max) / 2);
  const handleClick = () => setExpanded((expanded) => !expanded);
  const over = price > budget;

  return (
    <S.PriceStaticBar $over={over}>
      <S.CollapsedArea onClick={handleClick}>
        <S.Title>{TITLE}</S.Title>
        <Description $remainPrice={budget - price} />
        <ArrowDown className={expanded ? 'open' : null} />
      </S.CollapsedArea>
      <S.ExpandedArea className={expanded ? 'open' : null}>
        <Slider
          min={min}
          max={max}
          step={100000}
          disabled={!expanded}
          price={price}
          budget={budget}
          setBudget={setBudget}
          $over={over}
        />
        <SliderMark minPrice={min} maxPrice={max} />
      </S.ExpandedArea>
    </S.PriceStaticBar>
  );
};

export default PriceStaticBar;
