import { useState, useEffect } from 'react';
import { INFO } from '../constants';
import * as S from './style';
import Title from '../../../components/Title';
import HMGTag from '../../../components/HMGTag';
import HMGData from './HMGData';

function Info({ modelType }) {
  const HMGTagProps = { type: INFO.HMGTAG_TYPE };
  const [HMGDataProps, setHMGDataProps] = useState({
    hmgData: modelType.powerTrainOption?.hmgData,
  });
  const [imageProps, setImageProps] = useState(modelType.powerTrainOption?.imageUrl);
  const [titleProps, setTitleProps] = useState({
    type: INFO.TYPE,
    subTitle: '',
    mainTitle: '',
    info: '',
  });

  useEffect(() => {
    switch (modelType.pickId) {
      case modelType.powerTrainId:
        setTitleProps(() => ({
          type: INFO.TYPE,
          subTitle: modelType.powerTrainType,
          mainTitle: modelType.powerTrainOption?.name,
          info: modelType.powerTrainOption?.description,
        }));
        setImageProps(modelType.powerTrainOption?.imageUrl);
        setHMGDataProps({ hmgData: modelType.powerTrainOption?.hmgData });
        break;

      case modelType.bodyTypeId:
        setTitleProps(() => ({
          type: INFO.TYPE,
          subTitle: modelType.bodyTypeType,
          mainTitle: modelType.bodyTypeOption.name,
          info: modelType.bodyTypeOption.description,
        }));
        setImageProps(modelType.bodyTypeOption.imageUrl);
        setHMGDataProps({ hmgData: modelType.bodyTypeOption.hmgData });
        break;

      case modelType.wheelDriveId:
        setTitleProps(() => ({
          type: INFO.TYPE,
          subTitle: modelType.wheelDriveType,
          mainTitle: modelType.wheelDriveOption.name,
          info: modelType.wheelDriveOption.description,
        }));
        setImageProps(modelType.wheelDriveOption.imageUrl);
        setHMGDataProps({ hmgData: modelType.wheelDriveOption.hmgData });
        break;

      default:
        break;
    }
  }, [modelType]);

  return (
    <S.Info>
      <S.ModelText>
        <Title {...titleProps} />
        <S.HMG>
          {modelType.pickId === modelType.powerTrainId && <HMGTag {...HMGTagProps} />}
          <HMGData {...HMGDataProps} />
        </S.HMG>
      </S.ModelText>
      <S.ModelImage src={imageProps} />
    </S.Info>
  );
}

export default Info;
