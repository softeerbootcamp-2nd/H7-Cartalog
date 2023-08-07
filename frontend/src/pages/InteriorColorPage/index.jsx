import PropTypes from 'prop-types';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'InteriorColor';
const IMAGE_URL = '../../../../../assets/images/TrimSelect/interior.png';

function InteriorColor({ nextPage }) {
  const SectionProps = {
    type: TYPE,
    url: IMAGE_URL,
    Info: <Info />,
    Pick: <Pick nextPage={nextPage} />,
  };

  return <Section {...SectionProps} />;
}

InteriorColor.propTypes = {
  nextPage: PropTypes.func.isRequired,
};

export default InteriorColor;
