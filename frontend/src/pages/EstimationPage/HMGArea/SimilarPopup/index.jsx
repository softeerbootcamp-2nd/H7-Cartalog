import { useState } from 'react';
import { createPortal } from 'react-dom';
import { useData, TotalPrice } from '../../../../utils/Context';
import { SIMILAR, BUTTON, POPUP } from './constants';
import { ReactComponent as CloseIcon } from '../../../../../assets/icons/cancel.svg';
import * as S from './style';
import Button from '../../../../components/Button';
import Popup from '../../../../components/Popup';
import SimilarInfo from './SimilarInfo';
import PriceCompareBar from '../../../../components/PriceCompareBar';

function SimilarPopup({ show, close }) {
  const data = useData();
  const [exitShow, setExitShow] = useState(false);
  const handleExitShow = () => setExitShow(true);
  const handleExitClose = () => setExitShow(false);
  const [similarPage, setSimilarPage] = useState(0);
  const [similarPrice, setSimilarPrice] = useState(42239849); // 수정

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
