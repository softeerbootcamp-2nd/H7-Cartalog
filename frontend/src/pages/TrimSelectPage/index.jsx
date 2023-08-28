import { useState, useEffect } from 'react';
import { useData } from '../../utils/Context';
import { TRIM_SELECT } from './constants';
import Section from '../../components/Section';
import Guide from '../../components/Guide';
import Info from './Info';
import Pick from './Pick';
import useFetch from '../../hooks/useFetch';

function TrimSelect() {
  const { setTrimState, trim } = useData();
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const fetchedData = useFetch('models/trims?basicModelId=1');

  useEffect(() => {
    if (!fetchedData) return;
    setTrimState((prevState) => ({
      ...prevState,
      page: 1,
      trim: {
        ...prevState.trim,
        fetchData: [...fetchedData.trims],
        isFetch: true,
      },
    }));
  }, [fetchedData, setTrimState]);

  useEffect(() => {
    setShow(true);
  }, []);

  const SectionProps = {
    type: TRIM_SELECT.TYPE,
    Info: <Info trim={trim} />,
    Pick: <Pick setTrimState={setTrimState} trim={trim} />,
  };

  const GuideProps = {
    show,
    close: handleClose,
  };

  return (
    <>
      <Section {...SectionProps} />
      <Guide {...GuideProps} />
    </>
  );
}

export default TrimSelect;
