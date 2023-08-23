import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { EXTERIOR_COLOR, IMG_EXTENSION } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';
import useFetch from '../../hooks/useFetch';

function ExteriorColor() {
  const { setTrimState, page, trim, exteriorColor } = useData();
  const fetchedData = useFetch(`models/trims/exterior-colors?trimId=${trim.id}`);

  useEffect(() => {
    if (!fetchedData) return;
    async function fetchData() {
      if (exteriorColor.isFetch || page !== 3) return;
      const defaultData =
        fetchedData.exteriorColors.find((data) => data.code === exteriorColor.code) ??
        fetchedData.exteriorColors[0];

      setTrimState((prevState) => ({
        ...prevState,
        exteriorColor: {
          ...exteriorColor,
          fetchData: [...fetchedData.exteriorColors],
          page: fetchedData.exteriorColors.length - 3,
          isFetch: true,
          name: defaultData.name,
          carImageDirectory: defaultData.carImageDirectory,
          carImageList: Array.from({ length: 60 }, (_, index) => {
            const imageNumber = (index + 1).toString().padStart(3, '0');
            return `${defaultData.carImageDirectory}${imageNumber}${IMG_EXTENSION}`;
          }),
        },
      }));
    }
    fetchData();
  }, [exteriorColor, fetchedData, fetchedData?.exteriorColors, page, setTrimState]);

  const SectionProps = {
    type: EXTERIOR_COLOR.TYPE,
    Info: <Info setTrimState={setTrimState} exteriorColor={exteriorColor} />,
    Pick: <Pick setTrimState={setTrimState} exteriorColor={exteriorColor} />,
  };

  return <Section {...SectionProps} />;
}
export default ExteriorColor;
