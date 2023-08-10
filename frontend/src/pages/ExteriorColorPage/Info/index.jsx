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
    mainTitle: exteriorColor.pickName,
  };

  document.addEventListener('mouseup', () => {
    setTrimState((prevState) => ({
      ...prevState,
      exteriorColor: {
        ...prevState.exteriorColor,
        rotate: false,
      },
    }));
  });

  return (
    <S.Info
      onMouseDown={() => {
        setTrimState((prevState) => ({
          ...prevState,
          exteriorColor: {
            ...prevState.exteriorColor,
            rotate: true,
          },
        }));
      }}
    >
      <Title {...TitleProps} />
      <TrimStand />
      <TrimImage />
    </S.Info>
  );
}

export default Info;
