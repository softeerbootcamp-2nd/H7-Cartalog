import { useState } from 'react';
import PropTypes from 'prop-types';
import * as S from './style';

const IMG_DIRECTORY = '../../../../../assets/images/TrimSelect/';

function TrimImage({ trim }) {
  const IMAGE_NAME = ['exterior', 'interior', 'wheel'];
  const [page, setPage] = useState(0);

  return (
    <S.TrimImage>
      {IMAGE_NAME.map((name, index) => {
        const src = `${IMG_DIRECTORY}${trim}/${name}.png`;
        const handleClick = () => setPage(index);
        const className = index === page ? 'selected' : null;

        return (
          <S.TrimImageItem key={name} className={className} onClick={handleClick}>
            <img src={src} alt={name} />
          </S.TrimImageItem>
        );
      })}
    </S.TrimImage>
  );
}

TrimImage.propTypes = {
  trim: PropTypes.string.isRequired,
};

export default TrimImage;
