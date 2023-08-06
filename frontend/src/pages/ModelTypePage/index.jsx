import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'ModelType';
const IMAGE_URL = '../../../../../assets/images/ModelType/diesel.png';

function ModelType({ nextPage }) {
  const SectionProps = {
    type: TYPE,
    url: IMAGE_URL,
    Info: <Info />,
    Pick: <Pick nextPage={nextPage} />,
  };

  return <Section {...SectionProps} />;
}

export default ModelType;
