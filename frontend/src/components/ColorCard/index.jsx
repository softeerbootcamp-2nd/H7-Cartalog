import CheckIcon from '../CheckIcon';
import * as S from './style';

function ColorCard({ selected, pickRatio, name, price, onClick, children }) {
  return (
    <S.ColorCard className={selected ? 'selected' : null} onClick={onClick}>
      <S.ColorPreview>{children}</S.ColorPreview>
      <S.ColorInfo>
        <S.UpperInfo>
          <div className="pickRatio">
            <span>{pickRatio}%</span>가 선택했어요
          </div>
          <div className="title">{name}</div>
        </S.UpperInfo>
        <S.LowerInfo>
          <div className="price">+{price.toLocaleString()} 원</div>
          <CheckIcon />
        </S.LowerInfo>
      </S.ColorInfo>
    </S.ColorCard>
  );
}

export default ColorCard;
