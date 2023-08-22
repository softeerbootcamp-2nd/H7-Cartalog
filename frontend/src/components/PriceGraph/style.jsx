import { keyframes, styled } from 'styled-components';

const thumbAnimation = keyframes`
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
`;

export const PriceGraph = styled.div`
  position: relative;

  & > div {
    position: absolute;
    color: ${({ theme }) => theme.color.activeBlue};
    font: ${({ theme }) => theme.font.textKR.Medium14};
    animation: ${thumbAnimation} 1s ease forwards;
  }
`;

export const PriceGraphSvg = styled.svg`
  overflow: visible;

  & > path {
    stroke-width: 4px;
    stroke: ${({ theme }) => theme.color.gray['200']};
    stroke-dasharray: 1000;
  }

  & > circle {
    position: relative;
    opacity: 0;
    animation: ${thumbAnimation} 1s ease forwards;

    &:nth-of-type(1) {
      fill: ${({ theme }) => theme.color.blueBG};
      stroke-width: 2px;
      stroke: ${({ theme }) => theme.color.activeBlue};
    }

    &:nth-of-type(2) {
      fill: ${({ theme }) => theme.color.activeBlue};
    }
  }
`;
