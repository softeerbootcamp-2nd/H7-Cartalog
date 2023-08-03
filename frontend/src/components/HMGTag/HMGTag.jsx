import * as S from './HMGTagStyle';

function HMGTag(props) {
  return (
    <S.HMGTag style={{ height: props.weight }}>
      <S.Text>HMG Data</S.Text>
    </S.HMGTag>
  );
}

export default HMGTag;
