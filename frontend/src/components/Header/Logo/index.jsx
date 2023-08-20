import { useState } from 'react';
import { LOGO, POPUP } from '../constants';
import { ReactComponent as ArrowDown } from '../../../../assets/icons/arrow_down.svg';
import { ReactComponent as HyundaiLogo } from '../../../../assets/images/Hyundai_logo.svg';
import * as S from './style';
import Popup from '../../Popup';

function Logo() {
  const [show, setShow] = useState(false);
  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);

  const popupProps = {
    show,
    close: { handleClose },
    actions: [
      {
        secondary: true,
        text: POPUP.CANCEL,
      },
      {
        text: POPUP.EXIT,
        onClick: () => {
          window.location.href = POPUP.HYUNDAI_COM;
        },
      },
    ],
    children: POPUP.EXIT_TEXT,
  };

  return (
    <S.Logo>
      <S.LogoWrapper onClick={handleShow}>
        <HyundaiLogo />
      </S.LogoWrapper>

      <S.Line />
      <S.CarType>
        <S.Type>{LOGO.TYPE}</S.Type>
        <ArrowDown />
      </S.CarType>
      <Popup {...popupProps} />
    </S.Logo>
  );
}

export default Logo;
