import { useState } from 'react';
import * as S from './style';
import Selector from './Selector';
import HMGArea from '../../HMGArea';

function PackageInfo({ options }) {
  const [selected, setSelected] = useState(options[0].name);
  const selectedOption = options.find((option) => option.name === selected);

  return (
    <>
      <S.PackageInfo>
        <Selector options={options} selected={selected} setSelected={setSelected} />
        <div>{selectedOption?.description}</div>
      </S.PackageInfo>
      {selectedOption?.hmgData?.length !== 0 && <HMGArea data={selectedOption?.hmgData} />}
    </>
  );
}

export default PackageInfo;
