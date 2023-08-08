import React from 'react';
import * as S from './style';

function Popup({ show, close, actions, children }) {
  const actionButtons = actions.map((child) => {
    const handleClick = () => {
      if (child.onClick) {
        child.onClick();
      }
      close();
    };

    if (child.secondary) {
      return (
        <S.SecondaryPopupButton key={child.text} onClick={handleClick}>
          {child.text}
        </S.SecondaryPopupButton>
      );
    }
    return (
      <S.PopupButton key={child.text} onClick={handleClick}>
        {child.text}
      </S.PopupButton>
    );
  });
  return (
    show && (
      <>
        <S.Overlay onClick={close} />
        <S.Popup>
          <S.PopupContent>{children}</S.PopupContent>
          <S.PopupActions>{actionButtons}</S.PopupActions>
        </S.Popup>
      </>
    )
  );
}

export default Popup;
