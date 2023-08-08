import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'ModelType';
const IMAGE_URL = '../../../../../assets/images/ModelType/diesel.png';

function ModelType() {
  const InfoProps = { imageUrl: IMAGE_URL };
  const SectionProps = {
    type: TYPE,
    Info: <Info {...InfoProps} />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default ModelType;
