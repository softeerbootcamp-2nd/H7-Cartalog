import { useState, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { useData } from '../../../../utils/Context';
import { SIMILAR, BUTTON, POPUP } from './constants';
import { ReactComponent as CloseIcon } from '../../../../../assets/icons/cancel.svg';
import * as S from './style';
import Button from '../../../../components/Button';
import Popup from '../../../../components/Popup';
import SimilarInfo from './SimilarInfo';

function SimilarPopup({ show, close }) {
  const data = useData();
  const [exitShow, setExitShow] = useState(false);
  const handleExitShow = () => setExitShow(true);
  const handleExitClose = () => setExitShow(false);

  const buttonProps = {
    type: BUTTON.TYPE,
    state: BUTTON.STATE,
    mainTitle: BUTTON.MAIN_TITLE,
    event: () => {},
  };

  const popupProps = {
    show: exitShow,
    close: handleExitClose,
    actions: [
      {
        secondary: true,
        text: POPUP.CANCEL,
      },
      {
        text: POPUP.EXIT,
        onClick: close,
      },
    ],
    children: POPUP.EXIT_TEXT,
  };

  useEffect(() => {
    if (!data.page || data.page === 1) return;
    async function fetchData() {
      const response = await fetch(
        `http://13.209.9.2/models/images?exteriorColorCode=${data.exteriorColor.code}&interiorColorCode=${data.interiorColor.code}`,
      );
      const dataFetch = await response.json();

      data.setTrimState((prevState) => ({
        ...prevState,
        summary: {
          sideImage: dataFetch.sideExteriorImageUrl,
          interiorImage: dataFetch.interiorImageUrl,
        },
      }));
    }
    fetchData();
  }, [data]);

  return (
    <>
      {show &&
        createPortal(
          <>
            <S.Overlay />
            <S.SimilarPopup>
              <S.PopupWrapper>
                <S.Header>
                  <S.Info>
                    <S.Title>
                      <span>{SIMILAR.TITLE}</span>
                      {SIMILAR.SUB_TITLE}
                    </S.Title>
                    <S.TitleInfo>{SIMILAR.INFO_TITLE}</S.TitleInfo>
                  </S.Info>
                  <S.CloseButton>
                    <CloseIcon onClick={handleExitShow} />
                  </S.CloseButton>
                </S.Header>
                <S.Contents>
                  {/* 반복문 돌려야함 */}
                  <SimilarInfo />
                </S.Contents>
              </S.PopupWrapper>
              <S.Footer>
                <Button {...buttonProps} />
              </S.Footer>
            </S.SimilarPopup>
          </>,
          document.querySelector('#modal'),
        )}
      <Popup {...popupProps} />
    </>
  );
}

export default SimilarPopup;
