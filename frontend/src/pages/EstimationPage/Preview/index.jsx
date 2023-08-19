import { useState } from 'react';
import { useData } from '../../../utils/Context';
import * as S from './style';
import Toggle from '../../../components/Toggle';

function Preview() {
  const { trim, exteriorColor, interiorColor } = useData();
  const [toggle, setToggle] = useState(false);

  return (
    <S.Preview>
      <div>
        <S.CarInfo>
          <div className="title">{trim.name}</div>
          <div className="preview">
            <img
              src={interiorColor.carImageUrl}
              alt="interior"
              style={toggle ? null : { display: 'none' }}
            />
            <img
              src={`${exteriorColor.carImageDirectory}001.png`}
              alt="exterior"
              style={toggle ? { display: 'none' } : null}
            />
            <Toggle state={toggle} setState={setToggle} big />
          </div>
        </S.CarInfo>
      </div>
    </S.Preview>
  );
}

export default Preview;
