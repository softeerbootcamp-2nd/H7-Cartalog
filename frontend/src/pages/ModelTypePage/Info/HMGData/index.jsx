import React from 'react';
import * as S from './style';
import HMGChart from './HMGChart';

function HMGData({ hmgData }) {
  return (
    <S.HMGData>
      {hmgData?.map((data, index) => (
        <React.Fragment key={data.name}>
          <HMGChart {...data} />
          {index === 0 && <S.Divide />}
        </React.Fragment>
      ))}
    </S.HMGData>
  );
}

export default HMGData;
