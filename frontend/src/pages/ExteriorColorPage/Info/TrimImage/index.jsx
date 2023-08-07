import { useState } from 'react';
import PropTypes from 'prop-types';
import * as S from './style';

const palisadeUrl = 'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade';
const trimSelect = 'le-blanc';
const exteriorSelect = 'A2B';

function TrimImage({ start }) {
  const [image, setImage] = useState(1);
  const [prevX, setPrevX] = useState(0);

  const startSwipe = (event) => {
    if (start) {
      setPrevX(event.clientX);
      if (prevX < event.clientX) {
        if (image <= 1) setImage(60);
        else setImage((prevImage) => prevImage - 1);
      }
      if (prevX > event.clientX) {
        if (image >= 60) setImage(1);
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

TrimImage.propTypes = {
  start: PropTypes.bool.isRequired,
};

export default TrimImage;
