import { useEffect, useState } from 'react';
import { HMG_CHART } from '../../../constants';
import { useData } from '../../../../../utils/Context';
import * as S from './style';

/**
 * HMGChart를 그려주는 컴포넌트
 * @param name {string} 그래프 제목
 * @param measure {string} PS || kgf*m 값
 * @param rpm {string} rpm 값
 * @param value {string} 그래프의 width 값
 * @returns
 */
function HMGChart({ name, measure, rpm, value }) {
  const { modelType } = useData();
  const [chartValue, setChartValue] = useState(0);
  const chartProps = { chartValue };

  useEffect(() => {
    let ratio;

    if (name === HMG_CHART.OUTPUT) {
      ratio =
        modelType.powerTrainId === 1
          ? modelType.hmgData.diesel.output / modelType.hmgData.gasoline.output
          : modelType.hmgData.gasoline.output / modelType.hmgData.diesel.output;
    }
    if (name === HMG_CHART.TALK) {
      ratio =
        modelType.powerTrainId === 1
          ? modelType.hmgData.diesel.talk / modelType.hmgData.gasoline.talk
          : modelType.hmgData.gasoline.talk / modelType.hmgData.diesel.talk;
    }

    setChartValue(Math.min(ratio * 200, 200));
  }, [modelType.powerTrainId]);

  return (
    <S.HMGChart>
      <S.Title>
        {name}({measure})
      </S.Title>
      <S.Output>
        {value}
        {HMG_CHART.DIVIDE}
        {rpm}
      </S.Output>
      <S.Chart>
        <S.Bar {...chartProps} />
      </S.Chart>
    </S.HMGChart>
  );
}

export default HMGChart;
