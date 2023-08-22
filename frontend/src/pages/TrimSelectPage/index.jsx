import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { TRIM_SELECT } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';
import fetchData from '../../hooks/asdf/fetchData';

const resource = fetchData('models/trims?basicModelId=1');

function TrimSelect() {
  const fetchedData = resource.read();
  const { setTrimState } = useData();

  useEffect(() => {
    setTrimState((prevState) => ({
      ...prevState,
      page: 1,
      trim: {
        ...prevState.trim,
        fetchData: [...fetchedData.trims],
        isFetch: true,
      },
    }));
  }, [fetchedData.trims, setTrimState]);

  const SectionProps = {
    type: TRIM_SELECT.TYPE,
    Info: <Info />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default TrimSelect;
