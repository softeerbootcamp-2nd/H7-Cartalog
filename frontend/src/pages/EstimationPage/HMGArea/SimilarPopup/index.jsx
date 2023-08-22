import { useState, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { useData } from '../../../../utils/Context';
import { SIMILAR, BUTTON } from './constants';
import { ReactComponent as CloseIcon } from '../../../../../assets/icons/cancel.svg';
import * as S from './style';
import Button from '../../../../components/Button';

function SimilarPopup({ show, close }) {
  const { setTrimState, page, exteriorColor, interiorColor, summary } = useData();
  const [toggle, setToggle] = useState(false);
  const buttonProps = {
    type: BUTTON.TYPE,
    state: BUTTON.STATE,
    mainTitle: BUTTON.MAIN_TITLE,
    event: () => {},
  };

  useEffect(() => {
    if (!page || page === 1) return;
    async function fetchData() {
      const response = await fetch(
        `http://3.36.126.30/models/images?exteriorColorCode=${exteriorColor.code}&interiorColorCode=${interiorColor.code}`,
      );
      const dataFetch = await response.json();

      setTrimState((prevState) => ({
        ...prevState,
        summary: {
          sideImage: dataFetch.sideExteriorImageUrl,
          interiorImage: dataFetch.interiorImageUrl,
        },
      }));
    }
    fetchData();
    setToggle(false);
  }, [exteriorColor, interiorColor]);

  return (
    show &&
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

              {/* 유사가격견적 */}
              <S.CloseButton>
                <CloseIcon onClick={close} />
              </S.CloseButton>
            </S.Header>
            {/*  */}
            <S.Contents>
              <S.LeftArea>
                <img
                  src={summary.interiorImage}
                  alt="interior"
                  style={toggle ? null : { display: 'none' }}
                />
                <img
                  src={summary.sideImage}
                  alt="exterior"
                  style={toggle ? { display: 'none' } : null}
                />
              </S.LeftArea>
            </S.Contents>
          </S.PopupWrapper>
          <S.Footer>
            <Button {...buttonProps} />
          </S.Footer>
        </S.SimilarPopup>
      </>,
      document.querySelector('#modal'),
    )
  );
}

export default SimilarPopup;
