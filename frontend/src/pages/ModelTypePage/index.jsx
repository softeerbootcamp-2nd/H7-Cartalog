import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { MODEL_TYPE } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';
import useFetch from '../../hooks/useFetch';

function ModelType() {
  const { setTrimState, page, trim, modelType } = useData();
  const fetchedData = useFetch(`models/types?trimId=${trim.id}`);

  useEffect(() => {
    async function fetchData() {
      if (!modelType.isFetch && page === 2) {
        const findOptionByTypeAndId = (typeName, typeId) => {
          const type = fetchedData.modelTypes.find((data) => data.type === typeName);
          return type.options.find((name) => name.id === typeId);
        };

        const findOutputAndTorque = (powerTrainId) => {
          const powerTrain = fetchedData.modelTypes.find(
            (data) => data.type === modelType.powerTrainType,
          );
          const type = powerTrain.options.find((data) => data.id === powerTrainId).hmgData;
          const output = type.find((data) => data.name === '최대출력').value;
          const talk = type.find((data) => data.name === '최대토크').value;
          return { output, talk };
        };

        const updatedModelType = {
          ...modelType,
          fetchData: [...fetchedData.modelTypes],
          isFetch: true,
          powerTrainOption: findOptionByTypeAndId(modelType.powerTrainType, modelType.powerTrainId),
          bodyTypeOption: findOptionByTypeAndId(modelType.bodyTypeType, modelType.bodyTypeId),
          wheelDriveOption: findOptionByTypeAndId(modelType.wheelDriveType, modelType.wheelDriveId),
          hmgData: {
            diesel: { output: findOutputAndTorque(1).output, talk: findOutputAndTorque(1).talk },
            gasoline: { output: findOutputAndTorque(2).output, talk: findOutputAndTorque(2).talk },
          },
        };
        setTrimState((prevState) => ({
          ...prevState,
          trim: {
            ...prevState.trim,
            isDefault: true,
          },
          modelType: updatedModelType,
        }));
      }
    }
    fetchData();
  }, [fetchedData, modelType, page, setTrimState]);

  const SectionProps = {
    type: MODEL_TYPE.TYPE,
    Info: <Info modelType={modelType} />,
    Pick: <Pick modelType={modelType} />,
  };

  return <Section {...SectionProps} />;
}

export default ModelType;
