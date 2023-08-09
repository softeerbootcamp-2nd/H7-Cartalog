import { useState, useEffect } from 'react';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'ModelType';
const IMAGE_URL = '../../../../../assets/images/ModelType/diesel.png';

function ModelType() {
  const [modelType, setModelType] = useState([]);
  useEffect(() => {
    async function fetchData() {
      const response = await fetch('http://3.36.126.30/models/1/types');
      const dataFetch = await response.json();
      setModelType(dataFetch);
    }
    fetchData();
  }, []);

  console.log(modelType);

  const InfoProps = { imageUrl: IMAGE_URL };
  const SectionProps = {
    type: TYPE,
    Info: <Info {...InfoProps} />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default ModelType;
