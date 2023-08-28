import { useData } from '../../../utils/Context';
import { INFO } from '../constants';
import * as S from './style';
import Summary from './Summary';

function Info() {
  const { trim, modelType } = useData();

  const TRIM_DATA = [
    {
      title: INFO.MODEL,
      name: trim.name,
    },
    {
      title: INFO.DISPLACEMENT,
      name: `${modelType.hmgData.displacement.toLocaleString('ko-KR')}${INFO.DISPLACEMENT_UNIT}`,
    },
    {
      title: INFO.FUELEFFICIENCY,
      name: `${modelType.hmgData.fuelEfficiency}${INFO.FUELEFFICIENCY_UNIT}`,
    },
  ];

  return (
    <S.InfoWrapper>
      <S.Info>
        <div>{INFO.MENT}</div>
        <Summary data={TRIM_DATA} />
      </S.Info>
    </S.InfoWrapper>
  );
}

export default Info;
