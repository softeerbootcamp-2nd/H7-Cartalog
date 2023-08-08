import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'InteriorColor';
const IMAGE_URL = '../../../../../assets/images/TrimSelect/interior.png';

function InteriorColor() {
  const SectionProps = {
    type: TYPE,
    url: IMAGE_URL,
    Info: <Info />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default InteriorColor;
