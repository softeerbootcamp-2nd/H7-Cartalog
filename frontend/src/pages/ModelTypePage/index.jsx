import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { MODEL_TYPE } from './constants';
import Skeleton from '../../components/Skeleton';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function ModelType() {
  const { setTrimState, page, trim, modelType } = useData();

  useEffect(() => {
    async function fetchData() {
      if (!modelType.isFetch && page === 2) {
        const response = await fetch(`http://3.36.126.30/models/types?trimId=${trim.id}`);
        const dataFetch = await response.json();

        const findOptionByTypeAndId = (typeName, typeId) => {
          const type = dataFetch.modelTypes.find((data) => data.type === typeName);
          return type.options.find((name) => name.id === typeId);
        };

        const findOutputAndTalk = (powerTrainId) => {
          const powerTrain = dataFetch.modelTypes.find(
            (data) => data.type === modelType.powerTrainType,
          );
          const type = powerTrain.options.find((data) => data.id === powerTrainId).hmgData;
          const output = type.find((data) => data.name === '최대출력').value;
          const talk = type.find((data) => data.name === '최대토크').value;
          return { output, talk };
        };

        const updatedModelType = {
          ...modelType,
          fetchData: [...dataFetch.modelTypes],
          isFetch: true,
          powerTrainOption: findOptionByTypeAndId(modelType.powerTrainType, modelType.powerTrainId),
          bodyTypeOption: findOptionByTypeAndId(modelType.bodyTypeType, modelType.bodyTypeId),
          wheelDriveOption: findOptionByTypeAndId(modelType.wheelDriveType, modelType.wheelDriveId),
          hmgData: {
            diesel: { output: findOutputAndTalk(1).output, talk: findOutputAndTalk(1).talk },
            gasoline: { output: findOutputAndTalk(2).output, talk: findOutputAndTalk(2).talk },
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
  }, [page]);

  const SectionProps = {
    type: MODEL_TYPE.TYPE,
    Info: <Info />,
    Pick: <Pick />,
  };

  const SkeletonProps = {
    type: MODEL_TYPE.TYPE,
  };

  return modelType.isFetch ? <Section {...SectionProps} /> : <Skeleton {...SkeletonProps} />;
}

export default ModelType;
