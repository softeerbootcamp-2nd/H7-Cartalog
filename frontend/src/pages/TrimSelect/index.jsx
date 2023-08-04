import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function TrimSelect({ nextPage }) {
  return <Section type="TrimSelect" Info={<Info />} Select={<Pick nextPage={nextPage} />} />;
}

export default TrimSelect;
