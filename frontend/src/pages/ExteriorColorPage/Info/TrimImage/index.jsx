import { useState } from 'react';
import { useData } from '../../../../utils/Context';
import * as S from './style';

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
        src={`${exteriorColor.carImageDirectory}${image.toString().padStart(3, '0')}.png`}
        onMouseMove={startSwipe}
      />
    </S.TrimImage>
  );
}

export default TrimImage;
