import { useState } from 'react';
import Toggle from '../../../components/Toggle';
import * as S from './style';

const INTERIOR_IMAGE_SRC =
  'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/interior/I49/img.png';
const EXTERIOR_IMAGE_SRC =
  'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/exterior/A2B/001.png';

function Preview({ scrollY }) {
  const [toggle, setToggle] = useState(false);
  const delayStyle = {
    animationDelay: `${-scrollY / 440}s`,
  };

  return (
    <S.Preview>
      <S.CarInfo style={delayStyle}>
        <div className="title" style={delayStyle}>
          Le Blanc
        </div>
        <div className="preview" style={delayStyle}>
          <img
            src={INTERIOR_IMAGE_SRC}
            alt="interior"
            style={toggle ? delayStyle : { display: 'none', ...delayStyle }}
          />
          <img
            src={EXTERIOR_IMAGE_SRC}
            alt="exterior"
            style={toggle ? { display: 'none', ...delayStyle } : delayStyle}
          />
          <Toggle state={toggle} setState={setToggle} big />
        </div>
      </S.CarInfo>
    </S.Preview>
  );
}

export default Preview;
