import { useEffect } from 'react';
import Info from './Info';
import Pick from './Pick';
import Storage from '../../utils/Storage';

const API_LINK = 'http://3.36.126.30/models/';
const TRIM_ID = 1;

function EstimateFinish() {
  useEffect(() => {
    Storage({ dataName: 'initData', dataLink: `${API_LINK}trims?modelId=${TRIM_ID}`, trimId: 1 });
    Storage({ dataName: 'modelType', dataLink: `${API_LINK}${TRIM_ID}/types`, trimId: 1 });
    // Storage({
    //   dataName: 'exteriorColor',
    //   dataLink: `${API_LINK}/trims/exterior-colors?id=${TRIM_ID}`,
    //   trimId: 1,
    // });
  });
}

export default EstimateFinish;
