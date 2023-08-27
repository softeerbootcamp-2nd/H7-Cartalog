import { useEffect, useState } from 'react';
import * as S from './style';
import Toggle from '../../../../components/Toggle';

function CarImage({ interiorColor, exteriorColor, toggle }) {
  const interior = interiorColor.carImageUrl;
  const exterior = `${exteriorColor.carImageDirectory}001.webp`;

  return (
    <S.CarImage className={toggle ? 'expanded' : ''}>
      <img src={exterior} alt="exterior" className={toggle ? '' : 'visible'} />
      <img src={interior} alt="interior" className={`interior ${toggle ? 'visible' : ''}`} />
    </S.CarImage>
  );
}

export default CarImage;
