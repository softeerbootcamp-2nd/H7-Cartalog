import PropTypes from 'prop-types';
import * as S from './style';

function ColorCard({ selected, pickRatio, name, price, onClick, children }) {
  return (
    <S.ColorCard className={selected ? 'selected' : null} onClick={onClick}>
      <S.ColorPreview>{children}</S.ColorPreview>
      <S.ColorInfo>
        <S.UpperInfo>
          <S.PickRatio>
            <span>{pickRatio}%</span>가 선택했어요
          </S.PickRatio>
          <S.ColorName>{name}</S.ColorName>
        </S.UpperInfo>
        <S.LowerInfo>
          <S.Price>+{price.toLocaleString()} 원</S.Price>
          <S.CheckIcons />
        </S.LowerInfo>
      </S.ColorInfo>
    </S.ColorCard>
  );
}

ColorCard.propTypes = {
  selected: PropTypes.bool.isRequired,
  pickRatio: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
  price: PropTypes.number.isRequired,
  onClick: PropTypes.func.isRequired,
  children: PropTypes.string.isRequired,
};

export default ColorCard;
