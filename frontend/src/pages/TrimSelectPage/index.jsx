import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';
import { useData } from '../../utils/Context';
import { useEffect } from 'react';

const TYPE = 'TrimSelect';

function TrimSelect() {
  const { setState } = useData();
  useEffect(() => {
    setState((prevState) => ({
      ...prevState,
      page: 1,
    }));
  }, []);

  const SectionProps = {
    type: TYPE,
    Info: <Info />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default TrimSelect;
