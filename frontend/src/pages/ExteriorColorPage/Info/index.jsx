import { useEffect } from 'react';
import { INFO } from '../constants';
import * as S from './style';
import Title from '../../../components/Title';
import TrimImage from './TrimImage';
import TrimStand from './TrimStand';

function Info({ setTrimState, exteriorColor }) {
  const TitleProps = {
    type: INFO.TYPE,
    subTitle: INFO.SUB_TITLE,
    mainTitle: exteriorColor.name,
  };

  const handleMouseDown = () => {
    if (exteriorColor.rotate) return;
    setTrimState((prevState) => ({
      ...prevState,
      exteriorColor: {
        ...prevState.exteriorColor,
        rotate: true,
      },
    }));
  };

  useEffect(() => {
    const handleMouseUp = () => {
      if (!exteriorColor.rotate) return;
      setTrimState((prevState) => ({
        ...prevState,
        exteriorColor: {
          ...prevState.exteriorColor,
          rotate: false,
        },
      }));
    };
    document.addEventListener('mouseup', handleMouseUp);
    return () => {
      document.removeEventListener('mouseup', handleMouseUp);
    };
  }, [exteriorColor.rotate, setTrimState]);

  return (
    <S.Info onMouseDown={handleMouseDown}>
      <Title {...TitleProps} />
      <TrimStand />
      <TrimImage />
    </S.Info>
  );
}

export default Info;
