import { useState, useEffect } from 'react';
import { useData } from '../../utils/Context';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'ModelType';
const IMAGE_URL = '../../../../../assets/images/ModelType/diesel.png';

function ModelType() {
  const [isFetched, setIsFetched] = useState(false);
  const { setTrimState } = useData();

  useEffect(() => {
    async function fetchData() {
      const response = await fetch('http://3.36.126.30/models/1/types');
      const dataFetch = await response.json();

      setTrimState((prevState) => ({
        ...prevState,
        page: 2,
        modelType: {
          ...prevState.modelType,
          ...dataFetch,
          powerTrain: 1,
          wheelDrive: 2,
          bodyType: 1,
        },
        price: {
          ...prevState.price,
          powerTrainPrice: 1888000,
          bodyTypePrice: null,
          wheelDrivePrice: null,
        },
      }));
      setIsFetched(true);
    }
    fetchData();
  }, []);

  const InfoProps = { imageUrl: IMAGE_URL };
  const SectionProps = {
    type: TYPE,
    Info: <Info {...InfoProps} />,
    Pick: <Pick />,
  };
  return isFetched ? <Section {...SectionProps} /> : <>Loding</>;
}

export default ModelType;
