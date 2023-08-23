import { styled } from 'styled-components';
import { SlideFromBottom } from '../../../styles/GlobalStyle';

export const Preview = styled.div`
  width: 100%;
  background-color: ${({ theme }) => theme.color.white};

  & > div {
    background: ${({ theme }) => theme.color.finishGrad};
  }
`;

export const CarInfo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  width: 100%;
  height: 640px;
  margin: 0 auto;
  margin-top: 60px;
  padding-top: 72px;

  & > .title {
    ${SlideFromBottom}
    white-space: nowrap;
    z-index: 1;
    font: ${({ theme }) => theme.font.headKR.Bold150};
    color: ${({ theme }) => theme.color.white};
    font-display: swap;
    opacity: 0;
    animation-delay: 0.5s;
  }

  & > .preview {
    ${SlideFromBottom}
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 203px;
    opacity: 0;
    animation-delay: 1.5s;
    gap: 10px;

    & > img {
      width: 760px;
      height: 360px;
      object-fit: contain;
    }
  }
`;
