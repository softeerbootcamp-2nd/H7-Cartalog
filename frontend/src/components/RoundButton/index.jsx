import PropTypes from 'prop-types';
import * as S from './style';

/**
 * RoundButton을 보여주는 컴포넌트
 * @param clickEvent {event} 클릭했을 때 일어나는 이벤트 등록
 * @param type {string} price || option
 * @param state {string} active || hover || click || inactive
 * @param title {string} 버튼 이름 작성
 * @returns
 */
function RoundButton({ clickEvent, type, state, title }) {
  const ButtonProps = {
    type,
    $state: state,
  };

  return (
    <S.RoundButton>
      <S.Title onClick={clickEvent} {...ButtonProps}>
        {title}
      </S.Title>
    </S.RoundButton>
  );
}

RoundButton.propTypes = {
  clickEvent: PropTypes.func.isRequired,
  type: PropTypes.string.isRequired,
  state: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
};

export default RoundButton;
