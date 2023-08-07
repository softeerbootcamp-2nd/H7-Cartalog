import PropTypes from 'prop-types';
import { useState } from 'react';
import * as S from './style';

function Toggle({ big = false, state, setState, inactiveText = '외장', activeText = '내장' }) {
  const [offsetX, setOffsetX] = useState(0);
  const handleClick = (e, value) => {
    setState(value);
    setOffsetX(e.target.offsetLeft - 4);
  };

  return (
    <S.Toggle className={big ? 'big' : null}>
      <S.Selected style={{ transform: `translateX(${offsetX}px)` }} />
      <S.ToggleButton className={!state ? 'checked' : null} onClick={(e) => handleClick(e, false)}>
        {inactiveText}
      </S.ToggleButton>
      <S.ToggleButton className={state ? 'checked' : null} onClick={(e) => handleClick(e, true)}>
        {activeText}
      </S.ToggleButton>
    </S.Toggle>
  );
}

Toggle.propTypes = {
  big: PropTypes.bool.isRequired,
  state: PropTypes.string.isRequired,
  setState: PropTypes.func.isRequired,
  inactiveText: PropTypes.string.isRequired,
  activeText: PropTypes.string.isRequired,
};

export default Toggle;
