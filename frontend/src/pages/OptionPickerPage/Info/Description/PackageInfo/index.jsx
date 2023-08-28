import { useEffect, useState } from 'react';
import * as S from './style';
import Selector from './Selector';
import HMGArea from '../../HMGArea';

function PackageInfo({ name, options, setImageUrl }) {
  const defaultOptionName = options[0].name;
  const [selected, setSelected] = useState(defaultOptionName);
  const selectedOption = options.find((option) => option.name === selected);
  const handleSelect = ({ name, imageUrl }) => {
    setSelected(name);
    setImageUrl(imageUrl);
  };

  useEffect(() => {
    setSelected(defaultOptionName);
  }, [defaultOptionName, options]);

  return (
    <>
      <S.PackageInfo>
        <Selector name={name} options={options} selected={selected} handleSelect={handleSelect} />
        <div>{selectedOption?.description}</div>
      </S.PackageInfo>
      {selectedOption?.hmgData?.length !== 0 && <HMGArea data={selectedOption?.hmgData} />}
    </>
  );
}

export default PackageInfo;
