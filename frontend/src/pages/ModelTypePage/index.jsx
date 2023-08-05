import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function ModelType({ nextPage }) {
  return <Section type="ModelType" Info={<Info />} Pick={<Pick nextPage={nextPage} />} />;
}

export default ModelType;
