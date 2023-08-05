import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function AddOption({ nextPage }) {
  const imageUrl = '../../../../../assets/images/TrimSelect/interior.png';
  return (
    <Section type="AddOption" url={imageUrl} Info={<Info />} Pick={<Pick nextPage={nextPage} />} />
  );
}

export default AddOption;
