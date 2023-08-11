import * as S from './style';
import { useData } from '../../../../utils/Context';
import { HMG_DATA, HMG_TAG } from '../../constants';
import HMGTag from '../../../../components/HMGTag';
import HMGUnit from './HMGUnit';

// !FIXME Performance API 연결
const cc = 2199;
const km = 12;

function HMGData() {
  const { modelType } = useData();

  const tagProps = { type: HMG_TAG.TYPE };
  const displacementProps = {
    title: HMG_DATA.DISPLACEMENT,
    unit: cc.toLocaleString('ko-KR') + HMG_DATA.DISPLACEMENT_UNIT,
  };
  const fuelEfficiencyProps = {
    title: HMG_DATA.FUELEFFICIENCY,
    unit: km + HMG_DATA.FUELEFFICIENCY_UNIT,
  };

  return (
    <S.HMGData>
      <S.Info>
        <HMGTag {...tagProps} />
        <S.InfoText>
          <S.InfoHighlight>{modelType.powerTrainOption.name}</S.InfoHighlight>
          {HMG_DATA.AND}&nbsp;
          <S.InfoHighlight>{modelType.wheelDriveOption.name}</S.InfoHighlight>
          {HMG_DATA.OF}
        </S.InfoText>
        <S.InfoText>{HMG_DATA.INFO}</S.InfoText>
      </S.Info>
      <S.Detail>
        <HMGUnit {...displacementProps} />
        <S.Divide />
        <HMGUnit {...fuelEfficiencyProps} />
      </S.Detail>
    </S.HMGData>
  );
}

export default HMGData;
