import Section from '../../Section';
import Info from './Info';
import Pick from './Pick';

function Skeleton() {
  const SectionProps = {
    type: 'ExteriorColor',
    Info: <Info />,
    Pick: <Pick />,
  };

  return <Section {...SectionProps} />;
}

export default Skeleton;
