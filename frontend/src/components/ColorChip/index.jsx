import * as S from './style';

function ColorChip({ color, selected }) {
  return (
    <S.ColorChip className={selected ? 'selected' : null}>
      <div style={{ backgroundColor: `${color}` }}></div>
    </S.ColorChip>
  );
}

export default ColorChip;
