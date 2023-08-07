import React from 'react';
import * as S from './style';
import HMGChart from './HMGChart';

const TRIM_DATA = [
  {
    title: '최고출력(PS/rpm)',
    unit: '200',
    rpm: '3,800',
    width: '180px',
  },
  {
    title: '최대토크(kgf·m/rpm)',
    unit: '45',
    rpm: '1,750-2,750',
    width: '160px',
  },
];

function HMGData() {
  return (
    <S.HMGData>
      {TRIM_DATA.map((data, index) => (
        <React.Fragment key={data.title}>
          <HMGChart {...data} />
          {index === 0 && <S.Divide />}
        </React.Fragment>
      ))}
    </S.HMGData>
  );
}

export default HMGData;
