import { useEffect, cloneElement } from 'react';
import { useData } from '../../utils/Context';
import { TRIM_SELECT } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function TrimSelect() {
  const { setTrimState, trim } = useData();

  useEffect(() => {
    async function fetchData() {
      if (!trim.isFetch) {
        const response = await fetch('http://3.36.126.30/models/trims?basicModelId=1');
        const dataFetch = await response.json();

        setTrimState((prevState) => ({
          ...prevState,
          trim: {
            ...prevState.trim,
            fetchData: [...dataFetch.trims],
            isFetch: true,
          },
          clonePage: {
            ...prevState.clonePage,
            1: cloneElement(<TrimSelect />),
          },
        }));
      }
      setTrimState((prevState) => ({ ...prevState, page: 1 }));
      setTimeout(() => {
        setTrimState((prevState) => ({
          ...prevState,
          movePage: {
            ...prevState.movePage,
            clonePage: 1,
            nowContentRef: 'nowUnload',
            nextContentRef: 'nextUnload',
          },
        }));
      }, 1000);
    }
    fetchData();
  }, []);

  const SectionProps = {
    type: TRIM_SELECT.TYPE,
    Info: <Info />,
    Pick: <Pick />,
    showPriceStatic: false,
  };

  return trim.isFetch ? <Section {...SectionProps} /> : <div>loading...</div>;
}

export default TrimSelect;
