import { keyframes, styled } from 'styled-components';

const circleAnimation = keyframes`
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
    animation: ${circleAnimation} 0.5s ease forwards;

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
