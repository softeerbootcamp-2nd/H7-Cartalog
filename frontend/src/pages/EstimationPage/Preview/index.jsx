import { useState } from 'react';
import { useData } from '../../../utils/Context';
import * as S from './style';
import CarImage from './CarImage';
import Toggle from '../../../components/Toggle';

function Preview() {
  const { trim, exteriorColor, interiorColor } = useData();
  const [toggle, setToggle] = useState(false);

  return (
    <S.Preview>
      <S.CarInfo>
        <div className="title">{trim.name}</div>
        <CarImage toggle={toggle} interiorColor={interiorColor} exteriorColor={exteriorColor} />
        <div className="toggle">
          <Toggle state={toggle} setState={setToggle} big />
        </div>
      </S.CarInfo>
    </S.Preview>
  );
}

export default Preview;
