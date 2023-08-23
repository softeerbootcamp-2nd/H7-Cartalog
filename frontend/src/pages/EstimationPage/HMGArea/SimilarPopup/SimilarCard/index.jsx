import CheckIcon from '../../../../../components/CheckIcon';
import * as S from './style';

function SimilarCard({ selected, name, price, onClick, imageUrl }) {
  return (
    <S.SimilarCard className={selected ? 'selected' : null} onClick={onClick}>
      <S.SimilarImage src={imageUrl} />
      <S.SimilarInfo>
        <S.UpperInfo>{name}</S.UpperInfo>
        <S.LowerInfo>
          <div className="price">+{price.toLocaleString()} 원</div>
          <CheckIcon />
        </S.LowerInfo>
      </S.SimilarInfo>
    </S.SimilarCard>
  );
}

export default SimilarCard;
