import { useEffect, useState } from 'react';
import { useData } from '../../utils/Context';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'AddOption';
const IMAGE_URL = '../../../../../assets/images/TrimSelect/interior.png';

function OptionPicker() {
  const [isFetched, setIsFetched] = useState(false);
  const { setTrimState } = useData();

  useEffect(() => {
    async function fetchData() {
      // !FIX API 데이터 받아오도록 수정해야함
      setTrimState((prevState) => ({
        ...prevState,
        page: 5,
      }));
      setIsFetched(true);
    }
    fetchData();
  }, []);

  const InfoProps = { imageUrl: IMAGE_URL };
  const SectionProps = {
    type: TYPE,
    Info: <Info {...InfoProps} />,
    Pick: <Pick />,
  };

  return isFetched ? <Section {...SectionProps} /> : <>Loding...</>;
}

export default OptionPicker;
