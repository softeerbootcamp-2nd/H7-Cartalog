import PropTypes from 'prop-types';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'TrimSelect';

function TrimSelect({ nextPage }) {
  const SectionProps = {
    type: TYPE,
    Info: <Info />,
    Pick: <Pick nextPage={nextPage} />,
  };

  return <Section {...SectionProps} />;
}

TrimSelect.propTypes = {
  nextPage: PropTypes.func.isRequired,
};

export default TrimSelect;
