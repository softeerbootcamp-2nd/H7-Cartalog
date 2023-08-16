import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { INTERIOR_COLOR } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function InteriorColor() {
  const { setTrimState, page, trim, exteriorColor, interiorColor } = useData();

  useEffect(() => {
    async function fetchData() {
      if (!interiorColor.isFetch && page === 4) {
        const response = await fetch(
          `http://3.36.126.30/models/trims/interior-colors?exteriorColorCode=${exteriorColor.code}&trimId=${trim.id}`,
        );
        const dataFetch = await response.json();
        const defaultData = dataFetch.interiorColors.find(
          (data) => data.code === interiorColor.code,
        );

        setTrimState((prevState) => ({
          ...prevState,
          interiorColor: {
            ...interiorColor,
            fetchData: [...dataFetch.interiorColors],
            page: dataFetch.interiorColors.length - 3,
            isFetch: true,
            name: defaultData.name,
            carImageUrl: defaultData.carImageUrl,
          },
        }));
      }
    }
    fetchData();
  }, [page]);

  const SectionProps = {
    type: INTERIOR_COLOR.TYPE,
    url: interiorColor.carImageUrl,
    Info: <Info />,
    Pick: <Pick />,
  };

  return interiorColor.isFetch ? <Section {...SectionProps} /> : <>Loding</>;
}

export default InteriorColor;
