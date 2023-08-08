import CheckIcon from '../CheckIcon';
import * as S from './style';

function TypeCard({ selected, pickRatio, name, price, onClick }) {
  return (
    <S.TypeCard className={selected ? 'selected' : null} onClick={onClick}>
      <div className="pickRatio">
        <span>{pickRatio}%</span>의 선택
      </div>
      <div className="title">{name}</div>
      <S.Info>
        <div className="price">+{price.toLocaleString()} 원</div>
        <CheckIcon />
      </S.Info>
    </S.TypeCard>
  );
}

export default TypeCard;
