import { useState } from 'react';
import { useData } from '../../../../utils/Context';
import * as S from './style';

function TrimImage() {
  const { exteriorColor } = useData();
  const [count, setCount] = useState(0);
  const [prevX, setPrevX] = useState(0);

  const startSwipe = (event) => {
    if (!exteriorColor.rotate) return;
    const currentX = event.clientX;
    const diffX = currentX - prevX;
    setPrevX(currentX);

    if (diffX > 0) {
      setCount((prevImage) => (prevImage === 0 ? 59 : prevImage - 1));
    }
    if (diffX < 0) {
      setCount((prevImage) => (prevImage === 59 ? 0 : prevImage + 1));
    }
  };

  return (
    <S.TrimImage>
      {exteriorColor.carImageList.map((imagePath, index) => (
        <S.RotateImage
          key={imagePath}
          src={imagePath}
          style={{ display: index === count ? 'block' : 'none' }}
          onMouseMove={startSwipe}
        />
      ))}
    </S.TrimImage>
  );
}

export default TrimImage;
