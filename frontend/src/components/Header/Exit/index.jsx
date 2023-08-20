import { useState } from 'react';

import { EXIT, POPUP } from '../constants';
import { ReactComponent as Cancel } from '../../../../assets/icons/cancel.svg';
import * as S from './style';
import Popup from '../../Popup';

function Exit() {
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
    <S.Exit onClick={handleShow}>
      <S.Text>{EXIT.TEXT}</S.Text>
      <Cancel />
      <Popup {...popupProps} />
    </S.Exit>
  );
}

export default Exit;
