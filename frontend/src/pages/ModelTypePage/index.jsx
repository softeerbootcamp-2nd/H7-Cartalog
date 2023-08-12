import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { MODEL_TYPE } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function ModelType() {
  const { setTrimState, trim, modelType } = useData();

  useEffect(() => {
    async function fetchData() {
      if (!modelType.isFetch) {
        const response = await fetch(`http://3.36.126.30/models/types?basicModelId=${trim.Id}`);
        const dataFetch = await response.json();

        const findOptionByTypeAndId = (typeName, typeId) => {
          const type = dataFetch.modelTypes.find((data) => data.type === typeName);
          return type.options.find((name) => name.id === typeId);
        };

        const updatedModelType = {
          ...modelType,
          fetchData: [...dataFetch.modelTypes],
          isFetch: true,
          powerTrainOption: findOptionByTypeAndId(modelType.powerTrainType, modelType.powerTrainId),
          bodyTypeOption: findOptionByTypeAndId(modelType.bodyTypeType, modelType.bodyTypeId),
          wheelDriveOption: findOptionByTypeAndId(modelType.wheelDriveType, modelType.wheelDriveId),
        };

        setTrimState((prevState) => ({
          ...prevState,
          modelType: updatedModelType,
        }));
      }
      setTrimState((prevState) => ({ ...prevState, page: 2 }));
    }
    fetchData();
  }, []);

  const SectionProps = {
    type: MODEL_TYPE.TYPE,
    Info: <Info />,
    Pick: <Pick />,
  };
  return modelType.isFetch ? <Section {...SectionProps} /> : <>Loding</>;
}

export default ModelType;
