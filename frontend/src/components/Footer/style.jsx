import styled, { keyframes } from 'styled-components';

const slideUp = keyframes`
  0% {
    transform: translateY(120%);
  }
  100% {
    transform: translateY(0);
  }
`;
const slideDown = keyframes`
  0% {
    transform: translateY(0);
  }
  100% {
    transform: translateY(120%);
  }
`;

export const Footer = styled.div`
  position: fixed;
  z-index: 1;
  bottom: 0;

  display: flex;
  width: 100%;
  height: 155px;

  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(6px);

  &.visible {
    animation: ${slideUp} 0.7s ease-in-out forwards;
  }
  &.hidden {
    animation: ${slideDown} 0.7s ease-in-out forwards;
    transform: translateY(100%);
  }
`;

export const FooterEnd = styled.div`
  display: flex;
  flex-shrink: 0;
  justify-content: space-between;
  width: 1280px;
  margin: 0 auto;
  padding: 16px 128px;
  box-sizing: border-box;
`;

export const HMGDataFade = styled.div`
  &.visible {
    animation: ${slideUp} 0.7s ease-in-out forwards;
  }
  &.hidden {
    animation: ${slideDown} 0.7s ease-in-out forwards;
    transform: translateY(100%);
  }
`;
