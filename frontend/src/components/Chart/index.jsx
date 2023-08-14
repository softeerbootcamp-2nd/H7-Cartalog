import * as S from './style';

function Chart({ active, value, max }) {
  const height = (value / max) * 100;

  return (
    <S.Chart className={active ? 'active' : null}>
      <div>{value.toLocaleString()}대</div>
      <S.Bar $height={height} />
      <div>{active ? '내 견적' : '유사견적'}</div>
    </S.Chart>
  );
}

export default Chart;
