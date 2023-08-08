import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'TrimSelect';

function TrimSelect() {
  const SectionProps = {
    type: TYPE,
    Info: <Info />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default TrimSelect;
