import { useState } from 'react';
import * as S from './style';
import Selector from './Selector';

function PackageInfo({ options }) {
  const [selected, setSelected] = useState(options[0].name);

  return (
    <>
      <S.PackageInfo>
        <Selector options={options} selected={selected} setSelected={setSelected} />
      </S.PackageInfo>
      {options.find((option) => option.name === selected).description}
    </>
  );
}

export default PackageInfo;
