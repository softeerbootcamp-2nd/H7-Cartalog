import { useEffect, useState } from 'react';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';
import { useData } from '../../utils/Context';

const TYPE = 'TrimSelect';

function TrimSelect() {
  const [trimData, setTrimData] = useState(null);
  const { trimId, setTrimState } = useData();

  useEffect(() => {
    fetch('http://3.36.126.30/models/trims?modelId=1')
      .then((res) => res.json())
      .then((data) => setTrimData(data));
    setTrimState((prevState) => ({
      ...prevState,
      trimId: 1,
      page: 1,
    }));
  }, [setTrimState]);

  if (!trimData) return <div>loading...</div>;

  const SectionProps = {
    type: TYPE,
    Info: <Info trimId={trimId ?? 1} data={trimData?.trims} />,
    Pick: <Pick trimId={trimId ?? 1} setTrimState={setTrimState} data={trimData?.trims} />,
  };

  return <Section {...SectionProps} />;
}

export default TrimSelect;
