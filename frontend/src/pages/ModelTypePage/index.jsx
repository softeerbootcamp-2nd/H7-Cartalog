import { useState, useEffect } from 'react';
import { useData } from '../../utils/Context';
import { MODEL_TYPE } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function ModelType() {
  const { setTrimState, modelType } = useData();

  useEffect(() => {
    async function fetchData() {
      if (!modelType.isModelTypeFetch) {
        const response = await fetch('http://3.36.126.30/models/types?basicModelId=1');
        const dataFetch = await response.json();

        console.log(dataFetch.modelTypes);
        setTrimState((prevState) => ({
          ...prevState,
          modelType: {
            ...prevState.modelType,
            modelTypeFetch: [...dataFetch.modelTypes],
            isModelTypeFetch: true,
          },
        }));
      }
      setTrimState((prevState) => ({ ...prevState, page: 2 }));
    }
    fetchData();
  }, []);

  const SectionProps = {
    type: MODEL_TYPE.TYPE,
    // Info: <Info {...InfoProps} />,
    Pick: <Pick />,
  };
  return modelType.isModelTypeFetch ? <Section {...SectionProps} /> : <>Loding</>;
}

export default ModelType;
