import { useState, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { useData } from '../../../../utils/Context';
import { SIMILAR, BUTTON, POPUP } from './constants';
import { ReactComponent as CloseIcon } from '../../../../../assets/icons/cancel.svg';
import * as S from './style';
import Button from '../../../../components/Button';
import Popup from '../../../../components/Popup';
import SimilarInfo from './SimilarInfo';
import useFetch from '../../../../hooks/useFetch';

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

  // 데이터 받아와야함
  // const fetchedGetData = useFetch(
  //   `similarity/releases?estimateId=${data.estimation.id}&similarEstimateId=${data.trim.id}`,
  // );

  // useEffect(() => {
  //   if (!fetchedGetData || data.estimation.isFetch || data.page !== 6) return;
  //   data.setTrimState((prevState) => ({
  //     ...prevState,
  //     estimation: {
  //       ...prevState.estimation,
  //       isPost: true,
  //       id: fetchedPostData,
  //       isFetch: true,
  //       averagePrice: fetchedGetData,
  //     },
  //   }));
  // }, [data, fetchedGetData, fetchedPostData]);

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
