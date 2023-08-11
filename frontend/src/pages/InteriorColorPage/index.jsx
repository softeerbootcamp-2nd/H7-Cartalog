import { useState, useEffect } from 'react';
import { useData } from '../../utils/Context';
import { INTERIOR_COLOR } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

// !FIX 내장색상 API는 아직 완성 전
function InteriorColor() {
  const [isFetched, setIsFetched] = useState(false);
  const { setTrimState, trim, interiorColor } = useData();

  useEffect(() => {
    async function fetchData() {
      const response = await fetch(
        `http://3.36.126.30/models/trims/interior-colors?exteriorColorId=1&trimId=${trim.trimId}`,
      );
      const dataFetch = await response.json();
      setTrimState((prevState) => ({
        ...prevState,
        page: 4,
        interiorColor: {
          ...prevState.interiorColor,
          dataFetch: [...dataFetch.trimInteriorColorDtoList],
        },
      }));
      setIsFetched(true);
    }
    fetchData();
  }, []);

  const SectionProps = {
    type: INTERIOR_COLOR.TYPE,
    url: interiorColor.pickCarImageUrl,
    Info: <Info />,
    Pick: <Pick />,
  };

  return isFetched ? <Section {...SectionProps} /> : <>Loding</>;
}

export default InteriorColor;
