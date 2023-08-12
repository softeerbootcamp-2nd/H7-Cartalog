import { useState } from 'react';
import { useData } from '../../../../utils/Context';
import * as S from './style';

// !FIX : API완성되면 상수 없애기
const mockData =
  'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/le-blanc/exterior/A2B';

function TrimImage() {
  const { exteriorColor } = useData();
  const [image, setImage] = useState(1);
  const [prevX, setPrevX] = useState(0);

  const startSwipe = (event) => {
    if (!exteriorColor.rotate) return;
    const currentX = event.clientX;
    const diffX = currentX - prevX;
    setPrevX(currentX);

    if (diffX > 0) {
      setImage((prevImage) => (prevImage === 1 ? 60 : prevImage - 1));
    }
    if (diffX < 0) {
      setImage((prevImage) => (prevImage === 60 ? 1 : prevImage + 1));
    }
  };

  return (
    <S.TrimImage>
      <S.RotateImage
        src={`${mockData}/${image.toString().padStart(3, '0')}.png`}
        onMouseMove={startSwipe}
      />
    </S.TrimImage>
  );
}

export default TrimImage;
