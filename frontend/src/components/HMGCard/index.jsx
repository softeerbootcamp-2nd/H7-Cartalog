import HMGTag from '../HMGTag';
import * as S from './style';

function HMGCard({ title, description, children }) {
  return (
    <S.HMGCard>
      <HMGTag type="tag20" />
      <S.Contents>
        <S.Title>{title}</S.Title>
        {description && <S.Description>{description}</S.Description>}
        <S.Divider />
        {children}
      </S.Contents>
    </S.HMGCard>
  );
}

export default HMGCard;
