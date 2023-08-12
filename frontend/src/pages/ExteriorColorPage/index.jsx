import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { EXTERIOR_COLOR } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function ExteriorColor() {
  const { setTrimState, trim, exteriorColor } = useData();

  useEffect(() => {
    async function fetchData() {
      if (!exteriorColor.isExteriorColorFetch) {
        const response = await fetch(
          `http://3.36.126.30/models/trims/exterior-colors?trimId=${trim.trimId}`,
        );
        const dataFetch = await response.json();

        setTrimState((prevState) => ({
          ...prevState,
          exteriorColor: {
            ...exteriorColor,
            exteriorColorFetch: [...dataFetch.exteriorColors],
            page: dataFetch.exteriorColors.length - 3,
            isExteriorColorFetch: true,
          },
        }));
      }
      setTrimState((prevState) => ({ ...prevState, page: 3 }));
    }
    fetchData();
  }, []);

  const SectionProps = {
    type: EXTERIOR_COLOR.TYPE,
    // Info: <Info />,
    Pick: <Pick />,
  };

  return exteriorColor.isExteriorColorFetch ? <Section {...SectionProps} /> : <>Loding</>;
}

export default ExteriorColor;
