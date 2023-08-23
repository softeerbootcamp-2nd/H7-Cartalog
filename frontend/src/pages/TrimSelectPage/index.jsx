import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { TRIM_SELECT } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';
import useFetch from '../../hooks/useFetch';

function TrimSelect() {
  const { setTrimState, trim } = useData();
  const fetchedData = useFetch('models/trims?basicModelId=1');

  useEffect(() => {
    if (!fetchedData) return;
    setTrimState((prevState) => ({
      ...prevState,
      page: 1,
      trim: {
        ...prevState.trim,
        fetchData: [...fetchedData.trims],
        isFetch: true,
      },
    }));
  }, [fetchedData, setTrimState]);

  const SectionProps = {
    type: TRIM_SELECT.TYPE,
    Info: <Info trim={trim} />,
    Pick: <Pick setTrimState={setTrimState} trim={trim} />,
  };

  return <Section {...SectionProps} />;
}

export default TrimSelect;
