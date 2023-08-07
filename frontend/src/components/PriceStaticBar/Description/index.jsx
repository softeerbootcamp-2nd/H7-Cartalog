import PropTypes from 'prop-types';
import * as S from './style';

function Description({ $remainPrice }) {
  if ($remainPrice < 0)
    return (
      <S.Description>
        설정한 예산보다 <span className="over">{Math.abs($remainPrice).toLocaleString()}원</span> 더
        들었어요.
      </S.Description>
    );
  return (
    <S.Description>
      설정한 예산까지 <span>{$remainPrice.toLocaleString()}원</span> 남았어요.
    </S.Description>
  );
}

Description.propTypes = {
  $remainPrice: PropTypes.number.isRequired,
};

export default Description;
