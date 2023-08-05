import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function ExteriorColor({ nextPage }) {
  return <Section type="ExteriorColor" Info={<Info />} Pick={<Pick nextPage={nextPage} />} />;
}

export default ExteriorColor;
