import CheckIcon from '../../../components/CheckIcon';
import HMGTag from '../../../components/HMGTag';
import * as S from './style';

function OptionCard({
  name,
  isDefault,
  hasHmgData,
  pickRatio,
  hashtags = [],
  imgSrc,
  price,
  checked,
  selected,
  select,
  check,
}) {
  const priceString = isDefault ? '기본포함' : `+${price.toLocaleString()}원`;
  const id = `option-${name}`;
  const className = `${checked ? 'checked' : ''} ${selected ? 'selected' : ''}`;

  return (
    <S.OptionCard className={className} onClick={select}>
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
          {!isDefault && (
            <>
              <input
                id={id}
                type="checkbox"
                onClick={() => {
                  select();
                  check();
                }}
                style={{ display: 'none' }}
              />
              <label htmlFor={id} className="iconWrapper">
                {' '}
                <CheckIcon />
              </label>
            </>
          )}
        </S.LowerInfo>
      </S.OptionInfo>
    </S.OptionCard>
  );
}

export default OptionCard;
