import { useState, useEffect } from 'react';
import { useData } from '../../utils/Context';
import { EXTERIOR_COLOR } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function ExteriorColor() {
  const [isFetched, setIsFetched] = useState(false);
  const { setTrimState, trimId } = useData();

  useEffect(() => {
    async function fetchData() {
      const response = await fetch(`http://3.36.126.30/models/trims/exterior-colors?id=${trimId}`);
      const dataFetch = await response.json();

      setTrimState((prevState) => ({
        ...prevState,
        page: 3,
        exteriorColor: {
          ...prevState.exteriorColor,
          dataFetch: [...dataFetch.trimExteriorColorDtoList],
        },
      }));
      setIsFetched(true);
    }
    fetchData();
  }, []);

  const SectionProps = {
    type: EXTERIOR_COLOR.TYPE,
    Info: <Info />,
    Pick: <Pick />,
  };

  return isFetched ? <Section {...SectionProps} /> : <>Loding</>;
}

export default ExteriorColor;
