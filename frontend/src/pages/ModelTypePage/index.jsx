import { useEffect, cloneElement } from 'react';
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
        const response = await fetch(`http://3.36.126.30/models/types?basicModelId=${trim.id}`);
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
          trim: {
            ...prevState.trim,
            isDefault: true,
          },
          modelType: updatedModelType,
          clonePage: {
            ...prevState.clonePage,
            2: cloneElement(<ModelType />),
          },
        }));
      }
      setTrimState((prevState) => ({ ...prevState, page: 2 }));
      setTimeout(() => {
        setTrimState((prevState) => ({
          ...prevState,
          movePage: {
            ...prevState.movePage,
            clonePage: 2,
            nowContentRef: 'nowUnload',
            nextContentRef: 'nextUnload',
          },
        }));
      }, 1000);
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
