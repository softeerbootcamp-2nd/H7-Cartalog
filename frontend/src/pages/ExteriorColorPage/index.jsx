import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'ExteriorColor';

function ExteriorColor() {
  const SectionProps = {
    type: TYPE,
    Info: <Info />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default ExteriorColor;
