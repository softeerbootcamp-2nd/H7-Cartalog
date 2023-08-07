import PropTypes from 'prop-types';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'ExteriorColor';

function ExteriorColor({ nextPage }) {
  const SectionProps = {
    type: TYPE,
    Info: <Info />,
    Pick: <Pick nextPage={nextPage} />,
  };

  return <Section {...SectionProps} />;
}

ExteriorColor.propTypes = {
  nextPage: PropTypes.func.isRequired,
};

export default ExteriorColor;
