import { useEffect, useRef, useState } from 'react';
import Toggle from '../../../components/Toggle';
import * as S from './style';

const INTERIOR_IMAGE_SRC =
  'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/interior/I49/img.png';
const EXTERIOR_IMAGE_SRC =
  'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/exterior/A2B/001.png';

function Preview() {
  const [toggle, setToggle] = useState(false);

  return (
    <S.Preview>
      <div>
        <S.CarInfo>
          <div className="title">Le Blanc</div>
          <div className="preview">
            <img
              src={INTERIOR_IMAGE_SRC}
              alt="interior"
              style={toggle ? null : { display: 'none' }}
            />
            <img
              src={EXTERIOR_IMAGE_SRC}
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
