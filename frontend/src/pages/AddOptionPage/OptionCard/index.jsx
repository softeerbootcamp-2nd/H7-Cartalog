import CheckIcon from '../../../components/CheckIcon';
import HMGTag from '../../../components/HMGTag';
import * as S from './style';

function OptionCard({
  name,
  isDefault,
  hasHmgData,
  pickRatio,
  hashtags,
  imgSrc,
  price,
  selected,
  onClick,
}) {
  const priceString = isDefault ? '기본포함' : `+${price.toLocaleString()}원`;

  return (
    <S.OptionCard className={selected ? 'selected' : null} onClick={onClick}>
      <S.OptionImg>
        {hasHmgData && (
          <S.HMGTagWrapper>
            <HMGTag type="tag32" />
          </S.HMGTagWrapper>
        )}
        <img src={imgSrc} alt="option" />
        <S.HashTags>
          {hashtags.map((hashtag) => (
            <div key={hashtag}>{hashtag}</div>
          ))}
        </S.HashTags>
      </S.OptionImg>
      <S.OptionInfo>
        <S.UpperInfo>
          {!isDefault && (
            <div className="pickRatio">
              <span>{pickRatio}%</span>가 선택했어요
            </div>
          )}
          <div className="title">{name}</div>
        </S.UpperInfo>
        <S.LowerInfo>
          <div className="price">{priceString}</div>
          <div className="iconWrapper">
            <CheckIcon />
          </div>
        </S.LowerInfo>
      </S.OptionInfo>
    </S.OptionCard>
  );
}

export default OptionCard;
