import { useState } from 'react';
import { TRIM_IMAGE } from '../../constants';
import * as S from './style';

function TrimImage({ data }) {
  const [page, setPage] = useState(0);

  return (
    <S.TrimImage>
      {TRIM_IMAGE.NAME.map((name, index) => {
        const src = data[TRIM_IMAGE.NAME[index]];
        const className = index === page ? 'selected' : null;
        const handleClick = () => setPage(index);

        return (
          <S.TrimImageItem key={name} className={className} onClick={handleClick}>
            <img src={src} alt={name} />
          </S.TrimImageItem>
        );
      })}
    </S.TrimImage>
  );
}

export default TrimImage;
