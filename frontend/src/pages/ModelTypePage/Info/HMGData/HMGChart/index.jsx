import * as S from './style';

const OUTPUT_DIVIDE = '/';

/**
 * HMGChart를 그려주는 컴포넌트
 * @param title {string} 그래프 제목
 * @param unit {string} PS || kgf*m 값
 * @param rpm {string} rpm 값
 * @param width {string} 그래프의 width 값
 * @returns
 */
function HMGChart({ title, unit, rpm, width }) {
  const chartProps = { width };

  return (
    <S.HMGChart>
      <S.Title>{title}</S.Title>
      <S.Output>
        {unit}
        {OUTPUT_DIVIDE}
        {rpm}
      </S.Output>
      <S.Chart>
        <S.Bar {...chartProps}></S.Bar>
      </S.Chart>
    </S.HMGChart>
  );
}

export default HMGChart;
