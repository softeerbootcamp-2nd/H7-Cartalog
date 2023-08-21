import { useEffect, useState } from 'react';
import * as S from './style';
import Selector from './Selector';
import HMGArea from '../../HMGArea';

function PackageInfo({ name, options }) {
  const defaultOptionName = options[0].name;
  const [selected, setSelected] = useState(defaultOptionName);
  const selectedOption = options.find((option) => option.name === selected);

  useEffect(() => {
    setSelected(defaultOptionName);
  }, [defaultOptionName, options]);

  return (
    <>
      <S.PackageInfo>
        <Selector name={name} options={options} selected={selected} setSelected={setSelected} />
        <div>{selectedOption?.description}</div>
      </S.PackageInfo>
      {selectedOption?.hmgData?.length !== 0 && <HMGArea data={selectedOption?.hmgData} />}
    </>
  );
}

export default PackageInfo;
