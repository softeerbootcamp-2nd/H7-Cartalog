import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function InteriorColor({ nextPage }) {
  const imageUrl = '../../../../../assets/images/TrimSelect/interior.png';
  return (
    <Section
      type="InteriorColor"
      url={imageUrl}
      Info={<Info />}
      Pick={<Pick nextPage={nextPage} />}
    />
  );
}

export default InteriorColor;
