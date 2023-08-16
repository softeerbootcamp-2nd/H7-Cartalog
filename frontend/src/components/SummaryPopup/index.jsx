import { useState } from 'react';
import { createPortal } from 'react-dom';
import * as S from './style';
import Toggle from '../Toggle';
import InfoPanel from './InfoPanel';
import { ReactComponent as CloseIcon } from '../../../assets/icons/cancel.svg';

const INTERIOR_IMAGE_SRC =
  'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/interior/I49/img.png';
const EXTERIOR_IMAGE_SRC =
  'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/exterior/A2B/001.png';

const MOCK_DATA = [
  [
    { title: '모델', content: '팰리세이드', price: 35000000 },
    { title: '트림', content: 'Le Blanc (르블랑)' },
  ],
  [
    { title: '파워트레인', content: '디젤 2.2', price: 280000 },
    { title: '바디타입', content: '7인승', price: 0 },
    { title: '구동방식', content: '2WD', price: 0 },
  ],
  [
    { title: '외장색상', content: '포레스트 블루', price: 150000 },
    { title: '내장색상', content: '블랙', price: 0 },
  ],
  [{ title: '옵션', content: '-' }],
];

function SummaryPopup({ show, close, onClick }) {
  const [toggle, setToggle] = useState(false);

  return (
    show &&
    createPortal(
      <>
        <S.Overlay />
        <S.SummaryPopup>
          <S.Header>
            <S.Title>견적요약</S.Title>
            <S.CloseButton>
              <CloseIcon onClick={close} />
            </S.CloseButton>
          </S.Header>
          <S.Contents>
            <S.LeftArea>
              <img
                src={INTERIOR_IMAGE_SRC}
                alt="interior"
                style={toggle ? null : { display: 'none' }}
              />
              <img
                src={EXTERIOR_IMAGE_SRC}
                alt="exterior"
                style={toggle ? { display: 'none' } : null}
              />
              <Toggle state={toggle} setState={setToggle} big />
            </S.LeftArea>
            <InfoPanel data={MOCK_DATA} />
          </S.Contents>
          <S.PopupButton onClick={onClick}>견적 완료하기</S.PopupButton>
        </S.SummaryPopup>
      </>,
      document.querySelector('#modal'),
    )
  );
}

export default SummaryPopup;
