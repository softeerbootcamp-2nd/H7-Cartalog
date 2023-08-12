import { useEffect } from 'react';
import { useData } from '../../../utils/Context';
import { INFO } from '../constants';
import * as S from './style';
import Title from '../../../components/Title';
import TrimImage from './TrimImage';
import TrimStand from './TrimStand';

function Info() {
  const { setTrimState, exteriorColor } = useData();

  const TitleProps = {
    type: INFO.TYPE,
    subTitle: INFO.SUB_TITLE,
    mainTitle: exteriorColor.name,
  };

  const handleMouseDown = () => {
    setTrimState((prevState) => ({
      ...prevState,
      exteriorColor: {
        ...prevState.exteriorColor,
        rotate: true,
      },
    }));
  };

  const handleMouseUp = () => {
    setTrimState((prevState) => ({
      ...prevState,
      exteriorColor: {
        ...prevState.exteriorColor,
        rotate: false,
      },
    }));
  };

  useEffect(() => {
    document.addEventListener('mouseup', handleMouseUp);
    return () => {
      document.removeEventListener('mouseup', handleMouseUp);
    };
  }, []);

  return (
    <S.Info onMouseDown={handleMouseDown}>
      <Title {...TitleProps} />
      <TrimStand />
      <TrimImage />
    </S.Info>
  );
}

export default Info;
