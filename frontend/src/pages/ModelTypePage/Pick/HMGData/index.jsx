import { useState, useEffect } from 'react';
import { useData } from '../../../../utils/Context';
import { HMG_DATA, HMG_TAG } from '../../constants';
import * as S from './style';
import HMGTag from '../../../../components/HMGTag';
import HMGUnit from './HMGUnit';

function HMGData() {
  const { setTrimState, trim, modelType } = useData();
  const [hmgData, setHmgData] = useState({
    displacement: '',
    fuelEfficiency: '',
  });

  useEffect(() => {
    async function fetchData() {
      const response = await fetch(
        `http://3.36.126.30/models/trims/detail?modelTypeIds=${modelType.powerTrainId}&modelTypeIds=${modelType.bodyTypeId}&modelTypeIds=${modelType.wheelDriveId}&trimId=${trim.id}`,
      );
      const dataFetch = await response.json();

      setTrimState((prevState) => ({
        ...prevState,
        modelType: {
          ...prevState.modelType,
          detailTrimId: dataFetch.detailTrimId,
        },
      }));

      setHmgData({
        displacement: dataFetch.displacement,
        fuelEfficiency: dataFetch.fuelEfficiency,
      });
    }
    fetchData();
  }, [modelType.pickId]);

  const tagProps = { type: HMG_TAG.TYPE };
  const displacementProps = {
    title: HMG_DATA.DISPLACEMENT,
    unit: `${hmgData.displacement.toLocaleString('ko-KR')}${HMG_DATA.DISPLACEMENT_UNIT}`,
  };
  const fuelEfficiencyProps = {
    title: HMG_DATA.FUELEFFICIENCY,
    unit: `${hmgData.fuelEfficiency}${HMG_DATA.FUELEFFICIENCY_UNIT}`,
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
