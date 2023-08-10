import * as S from './style';

/**
 * 색상 카드에서 이미지를 보여주는 컴포넌트
 * @param selected {bool} 현재 색상이 골라진 색상인지에 대한 bool타입
 * @param src {string} 색상 카드에 들어갈 이미지 url
 * @param type {string} ExteriorColor || InteriorColor
 * @returns
 */
function ColorChip({ selected, src, type }) {
  const ColorChipProps = {
    type,
    className: selected ? 'selected' : null,
  };

  return (
    <S.ColorChip {...ColorChipProps}>
      <img src={src} alt="color" />
    </S.ColorChip>
  );
}

export default ColorChip;
