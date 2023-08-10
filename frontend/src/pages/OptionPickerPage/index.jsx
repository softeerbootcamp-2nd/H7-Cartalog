import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'AddOption';
const IMAGE_URL = '../../../../../assets/images/TrimSelect/interior.png';

function OptionPicker() {
  const InfoProps = { imageUrl: IMAGE_URL };
  const SectionProps = {
    type: TYPE,
    Info: <Info {...InfoProps} />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default OptionPicker;
