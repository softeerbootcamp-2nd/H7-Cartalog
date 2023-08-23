import { useEffect } from 'react';
import { useData } from '../../utils/Context';
import { INTERIOR_COLOR } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';
import useFetch from '../../hooks/useFetch';

function InteriorColor() {
  const { setTrimState, page, trim, exteriorColor, interiorColor } = useData();
  const fetchedData = useFetch(
    `models/trims/interior-colors?exteriorColorCode=${exteriorColor.code}&trimId=${trim.id}`,
  );

  useEffect(() => {
    if (!fetchedData || interiorColor.isFetch || page !== 4) return;
    const defaultData = fetchedData.interiorColors.find((data) => data.code === interiorColor.code);

    setTrimState((prevState) => ({
      ...prevState,
      interiorColor: {
        ...interiorColor,
        fetchData: [...fetchedData.interiorColors],
        page: fetchedData.interiorColors.length - 3,
        isFetch: true,
        name: defaultData.name,
        carImageUrl: defaultData.carImageUrl,
      },
    }));
  }, [fetchedData, interiorColor, page, setTrimState]);

  const SectionProps = {
    type: INTERIOR_COLOR.TYPE,
    url: interiorColor.carImageUrl,
    Info: <Info interiorColor={interiorColor} />,
    Pick: <Pick setTrimState={setTrimState} interiorColor={interiorColor} />,
  };

  return <Section {...SectionProps} />;
}

export default InteriorColor;
