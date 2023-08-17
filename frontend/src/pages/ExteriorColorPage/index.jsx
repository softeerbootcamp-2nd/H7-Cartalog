import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { EXTERIOR_COLOR } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function ExteriorColor() {
  const { setTrimState, page, trim, exteriorColor } = useData();

  useEffect(() => {
    async function fetchData() {
      if (!exteriorColor.isFetch && page === 3) {
        const response = await fetch(
          `http://3.36.126.30/models/trims/exterior-colors?trimId=${trim.id}`,
        );
        const dataFetch = await response.json();
        const defaultData = dataFetch.exteriorColors.find(
          (data) => data.code === exteriorColor.code,
        );

        setTrimState((prevState) => ({
          ...prevState,
          exteriorColor: {
            ...exteriorColor,
            fetchData: [...dataFetch.exteriorColors],
            page: dataFetch.exteriorColors.length - 3,
            isFetch: true,
            name: defaultData.name,
            carImageDirectory: defaultData.carImageDirectory,
            carImageList: Array.from({ length: 60 }, (_, index) => {
              const imageNumber = (index + 1).toString().padStart(3, '0');
              return `${defaultData.carImageDirectory}${imageNumber}.png`;
            }),
          },
        }));
      }
    }
    fetchData();
  }, [page]);

  const SectionProps = {
    type: EXTERIOR_COLOR.TYPE,
    Info: <Info />,
    Pick: <Pick />,
    showPriceStatic: true,
  };

  return exteriorColor.isFetch ? <Section {...SectionProps} /> : <>Loding</>;
}

export default ExteriorColor;
