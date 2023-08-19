import { keyframes, styled } from 'styled-components';

const CarInfoAnimation = keyframes`
  0% {
    height: 640px;
  }

  100% {
    height: 232px;
  }
`;

const TitleAnimation = keyframes`
  0% {
    top: 72px;
    left: 50%;
    transform: translateX(-50%);
  }

  100% {
    top: 0;
    left: 70px;
    transform: translateX(-20%) scale(0.6);
  }
`;

const PreviewAnimation = keyframes`
  0% {
    top: 203px;
    right: 50%;
    transform: translateX(50%);
  }

  100% {
    top: 0;
    right: 0;
    transform: none;
  }
`;

const ImageAnimation = keyframes`
  0% {
    width: 760px;
    height: 360px;
  }

  100% {
    width: 280px;
    height: 180px;
  }
`;

export const Preview = styled.div`
  width: 100%;
  height: 680px;
  background-color: ${({ theme }) => theme.color.white};

  & > div {
    background: ${({ theme }) => theme.color.finishGrad};
  }
`;

export const CarInfo = styled.div`
  position: relative;
  width: 100%;
  margin: 0 auto;
  margin-top: 60px;
  animation: ${CarInfoAnimation} 1s linear forwards;
  animation-play-state: paused;

  & > .title {
    position: absolute;
    top: 72px;
    left: 50%;
    transform: translateX(-50%);
    white-space: nowrap;
    z-index: 1;

    font: ${({ theme }) => theme.font.headKR.Bold150};
    color: ${({ theme }) => theme.color.white};
    animation: ${TitleAnimation} 1s linear forwards;
    animation-play-state: paused;
  }

  & > .preview {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: absolute;
    transform: translateX(50%);
    animation: ${PreviewAnimation} 1s linear forwards;
    animation-play-state: paused;
    gap: 10px;

    & > img {
      width: 760px;
      height: 360px;
      object-fit: contain;
      animation: ${ImageAnimation} 1s linear forwards;
      animation-play-state: paused;
    }
  }
`;
