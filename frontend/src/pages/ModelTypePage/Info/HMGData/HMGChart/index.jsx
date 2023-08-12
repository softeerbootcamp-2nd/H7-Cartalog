import * as S from './style';

const OUTPUT_DIVIDE = '/';

/**
 * HMGChart를 그려주는 컴포넌트
 * @param name {string} 그래프 제목
 * @param measure {string} PS || kgf*m 값
 * @param rpm {string} rpm 값
 * @param value {string} 그래프의 width 값
 * @returns
 */
function HMGChart({ name, measure, rpm, value }) {
  // !FIXME 최고출력, 최대토크 그래프 width 수정
  const chartProps = { value };

  return (
    <S.HMGChart>
      <S.Title>
        {name}({measure})
      </S.Title>
      <S.Output>
        {value}
        {OUTPUT_DIVIDE}
        {rpm}
      </S.Output>
      <S.Chart>
        <S.Bar {...chartProps} />
      </S.Chart>
    </S.HMGChart>
  );
}

export default HMGChart;
