import { useState } from 'react';
import * as S from './style';

const palisadeUrl = 'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade';
const trimSelect = 'le-blanc';
const exteriorSelect = 'A2B';

function TrimImage({ trim, start }) {
  const [image, setImage] = useState(1);
  const [prevX, setPrevX] = useState(0);

  const startSwipe = (event) => {
    if (start) {
      setPrevX(event.clientX);
      if (prevX < event.clientX) {
        if (1 >= image) setImage(60);
        else setImage((prevImage) => prevImage - 1);
      }
      if (prevX > event.clientX) {
        if (60 <= image) setImage(1);
        else setImage((prevImage) => prevImage + 1);
      }
    }
  };

  return (
    <S.TrimImage>
      <S.Image
        src={`${palisadeUrl}/${trimSelect}/exterior/${exteriorSelect}/${image
          .toString()
          .padStart(3, '0')}.png`}
        onMouseMove={startSwipe}
      />
    </S.TrimImage>
  );
}

export default TrimImage;
