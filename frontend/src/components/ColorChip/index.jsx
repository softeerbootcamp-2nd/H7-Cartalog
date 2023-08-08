import * as S from './style';

function ColorChip({ selected, src }) {
  return (
    <S.ColorChip className={selected ? 'selected' : null}>
      <img src={src} alt="color" />
    </S.ColorChip>
  );
}

export default ColorChip;
