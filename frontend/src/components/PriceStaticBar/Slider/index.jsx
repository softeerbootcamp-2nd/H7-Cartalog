import * as S from './style';

function Slider({ min = 0, max = 100, step = 1, disabled, price, budget, setBudget }) {
  const getRatio = (value) => (value - min) / (max - min);
  const getRatioText = (value) => `calc(${value * 100}% + ${(0.5 - value) * 20 - 1}px)`;
  const priceRatio = getRatio(price);
  const priceRatioText = getRatioText(priceRatio);
  const fillRatio = getRatio(budget);
  const fillRatioText = getRatioText(fillRatio);
  const handleChange = (e) => setBudget(e.target.value);
  const overClass = price > budget ? 'over' : null;

  return (
    <S.SliderContainer>
      <div className={`background ${overClass}`} />
      <div className="fill" style={{ width: fillRatioText }} />
      <S.Pin style={{ left: priceRatioText }} className={overClass} />
      <S.Slider
        type="range"
        value={budget}
        min={min}
        max={max}
        step={step}
        disabled={disabled}
        onChange={handleChange}
        className={overClass}
      />
    </S.SliderContainer>
  );
}

export default Slider;
