import PropTypes from 'prop-types';
import * as S from './style';

function ColorChip({ color, selected }) {
  return (
    <S.ColorChip className={selected ? 'selected' : null}>
      <div style={{ backgroundColor: `${color}` }} />
    </S.ColorChip>
  );
}

ColorChip.propTypes = {
  color: PropTypes.string.isRequired,
  selected: PropTypes.bool.isRequired,
};

export default ColorChip;
