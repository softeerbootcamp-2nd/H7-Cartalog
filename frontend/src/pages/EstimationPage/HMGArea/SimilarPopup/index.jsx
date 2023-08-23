import { useState } from 'react';
import { createPortal } from 'react-dom';
import { useData, TotalPrice } from '../../../../utils/Context';
import {
  SIMILAR,
  SELECT_BUTTON,
  ADD_BUTTON,
  SELECT_POPUP,
  ADD_POPUP,
  OPTION_POPUP,
} from './constants';
import { ReactComponent as CloseIcon } from '../../../../../assets/icons/cancel.svg';
import * as S from './style';
import Button from '../../../../components/Button';
import Popup from '../../../../components/Popup';
import SimilarInfo from './SimilarInfo';
import PriceCompareBar from '../../../../components/PriceCompareBar';

function SimilarPopup({ show, close }) {
  const data = useData();
  const [addClicked, setAddClicked] = useState(false);
  const [exitShow, setExitShow] = useState(false);
  const handleExitShow = () => setExitShow(true);
  const handleExitClose = () => setExitShow(false);
  const [similarPage, setSimilarPage] = useState(0);
  const [similarPrice, setSimilarPrice] = useState(42239849);

  const selectButtonProps = {
    type: SELECT_BUTTON.TYPE,
    state: SELECT_BUTTON.STATE,
    mainTitle: SELECT_BUTTON.MAIN_TITLE,
    event: () => {},
  };

  const addButtonProps = {
    type: ADD_BUTTON.TYPE,
    state: ADD_BUTTON.STATE,
    subTitle: `${ADD_BUTTON.SUB_TITLE} ${data.estimation.similarEstimateOption.length}개`,
    mainTitle: ADD_BUTTON.MAIN_TITLE,
    event: () => {
      handleExitShow();
      setAddClicked(true);
    },
  };

  const selectPopupProps = {
    show: exitShow,
    close: handleExitClose,
    actions: [
      {
        secondary: true,
        text: SELECT_POPUP.CANCEL,
      },
      {
        text: SELECT_POPUP.EXIT,
        onClick: close,
      },
    ],
    children: SELECT_POPUP.EXIT_TEXT,
  };

  const addPopupProps = {
    show: exitShow,
    close: handleExitClose,
    actions: [
      {
        secondary: true,
        text: ADD_POPUP.CANCEL,
      },
      {
        text: ADD_POPUP.EXIT,
        onClick: close,
      },
    ],
    children: ADD_POPUP.EXIT_TEXT,
  };

  const optionPopupProps = {
    show: exitShow,
    close: handleExitClose,
    actions: [
      {
        text: OPTION_POPUP.EXIT,
        onClick: () => {
          close();
          setAddClicked(false);
        },
      },
    ],
    children: OPTION_POPUP.EXIT_TEXT,
  };
  console.log(data.estimation.similarEstimateCountInfo);

  return (
    <>
      {show &&
        createPortal(
          <>
            <S.Overlay />
            <S.SimilarPopup>
              <S.PopupWrapper>
                <S.Header>
                  <S.InfoWrapper>
                    <S.Info>
                      <S.Title>
                        <span>{SIMILAR.TITLE}</span>
                        {SIMILAR.SUB_TITLE}
                      </S.Title>
                      <S.TitleInfo>{SIMILAR.INFO_TITLE}</S.TitleInfo>
                    </S.Info>
                    <PriceCompareBar
                      min={data.trim.minPrice}
                      max={data.trim.maxPrice}
                      my={TotalPrice(data.price)}
                      value={similarPrice}
                    />
                  </S.InfoWrapper>
                  <S.CloseButton>
                    <CloseIcon onClick={handleExitShow} />
                  </S.CloseButton>
                </S.Header>
                <S.Contents style={{ transform: `translateX(${-similarPage * 772}px)` }}>
                  {data.estimation.similarEstimateCountInfo.map((info) => (
                    <SimilarInfo
                      key={info.estimateInfo.detailTrimId}
                      info={info.estimateInfo}
                      page={similarPage}
                      setPage={setSimilarPage}
                      setPrice={setSimilarPrice}
                    />
                  ))}
                </S.Contents>
              </S.PopupWrapper>
              <S.Footer>
                {data.estimation.similarEstimateOption.length === 0 ? (
                  <Button {...selectButtonProps} />
                ) : (
                  <Button {...addButtonProps} />
                )}
              </S.Footer>
            </S.SimilarPopup>
          </>,
          document.querySelector('#modal'),
        )}
      {(() => {
        if (addClicked) return <Popup {...optionPopupProps} />;
        if (data.estimation.similarEstimateOption.length === 0)
          return <Popup {...selectPopupProps} />;
        if (data.estimation.similarEstimateOption.length !== 0) return <Popup {...addPopupProps} />;
        return null;
      })()}
    </>
  );
}

export default SimilarPopup;
