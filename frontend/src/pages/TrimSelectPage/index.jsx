import { useEffect, useState } from 'react';
import { useData } from '../../utils/Context';
import { TRIM_SELECT } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function TrimSelect() {
  const [isFetched, setIsFetched] = useState(false);
  const { setTrimState, trim } = useData();

  useEffect(() => {
    async function fetchData() {
      if (!trim.isTrimFetch) {
        const response = await fetch('http://3.36.126.30/models/trims?basicModelId=1');
        const dataFetch = await response.json();

        setTrimState((prevState) => ({
          ...prevState,
          page: 1,
          trim: {
            ...prevState.trim,
            trimFetch: [...dataFetch.trims],
            isTrimFetch: true,
          },
        }));
        setIsFetched(true);
      }
    }
    fetchData();
  }, []);

  //
  console.log(trim);

  const SectionProps = {
    type: TRIM_SELECT.TYPE,
    // Info: <Info trimId={trimId ?? 1} data={trimData?.trims} />,
    Pick: <Pick />,
    showPriceStatic: false,
  };

  return isFetched ? <Section {...SectionProps} /> : <div>loading...</div>;
}

export default TrimSelect;
