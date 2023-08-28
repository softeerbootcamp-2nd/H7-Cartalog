import React from 'react';
import { createPortal } from 'react-dom';
import * as S from './style';
import HMGInfo from '../../pages/TrimSelectPage/Info/HMGInfo';
import { SAMPLE_DATA } from './constants';

function Guide({ show, close }) {
  return (
    show &&
    createPortal(
      <>
        <S.Overlay onClick={close} />
        <S.LayoutWrapper>
          <S.PopupWrapper>
            <S.Popup>
              <S.Header>
                <S.PopupContent>
                  현대자동차만이
                  <div>
                    제공하는&nbsp;
                    <S.Highlight>실활용 데이터</S.Highlight>로
                  </div>
                  합리적인 차량을 만들어 보세요.
                </S.PopupContent>
                <S.CancelButton onClick={close}>
                  <S.CancelIcon />
                </S.CancelButton>
              </S.Header>
              <S.Divide />
              <S.InfoContent>
                HMG Data 마크는 Hyundai Motor Group에서만 제공하는 데이터입니다.
                <br />
                주행 중 운전자들이 실제로 얼마나 활용하는지를 추적해 수치화한 데이터 입니다.
              </S.InfoContent>
            </S.Popup>
          </S.PopupWrapper>
          <S.SampleInfo>
            <HMGInfo data={SAMPLE_DATA} />
          </S.SampleInfo>
        </S.LayoutWrapper>
      </>,
      document.querySelector('#guide'),
    )
  );
}

export default Guide;
