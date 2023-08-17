import { useState, useEffect } from 'react';
import { useData, TotalPrice } from '../../utils/Context';
import { ReactComponent as ArrowDown } from '../../../assets/icons/arrow_down.svg';
import { PRICE_STATIC_BAR } from './constant';
import * as S from './style';
import Description from './Description';
import Slider from './Slider';
import SliderMark from './SliderMark';

function PriceStaticBar() {
  const { page, trim, price, budget } = useData();
  const [isVisible, setIsVisible] = useState(false);
  const [expanded, setExpanded] = useState(false);
  const handleClick = () => setExpanded((isExpanded) => !isExpanded);
  const over = TotalPrice(price) > budget;

  useEffect(() => {
    setIsVisible(page !== 1);
  }, [page]);

  return (
    <S.PriceStaticBar $over={over} className={isVisible ? '' : 'hidden'}>
      <S.CollapsedArea onClick={handleClick}>
        <S.Title>{PRICE_STATIC_BAR.TITLE}</S.Title>
        <Description $remainPrice={budget - TotalPrice(price)} />
        <ArrowDown className={expanded ? 'open' : null} />
      </S.CollapsedArea>
      <S.ExpandedArea className={expanded ? 'open' : null}>
        <Slider
          min={trim?.minPrice}
          max={trim?.maxPrice}
          step={100000}
          disabled={!expanded}
          price={TotalPrice(price)}
          budget={budget}
          $over={over}
        />
        <SliderMark minPrice={trim.minPrice} maxPrice={trim.maxPrice} />
      </S.ExpandedArea>
    </S.PriceStaticBar>
  );
}
export default PriceStaticBar;
