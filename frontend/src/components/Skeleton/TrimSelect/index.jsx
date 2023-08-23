import Section from '../../Section';
import Info from './Info';
import Pick from './Pick';

function Skeleton({ type }) {
  const SectionProps = {
    type,
    Info: <Info />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default Skeleton;
