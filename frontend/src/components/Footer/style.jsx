import styled, { css } from 'styled-components';
import { EASE_OUT_CUBIC } from '../../constants';

const slideCss = css`
  transform: translateY(120%);
  transition: transform 0.5s ${EASE_OUT_CUBIC};

  &.visible {
    transform: translateY(0);
  }
`;

export const Footer = styled.div`
  ${slideCss}
  display: flex;
  width: 100%;
  position: fixed;
  bottom: 0;
  z-index: 1;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(6px);
`;

export const FooterEnd = styled.div`
  display: flex;
  flex-shrink: 0;
  justify-content: space-between;
  width: 1280px;
  margin: 0 auto;
  padding: 0 128px;
  box-sizing: border-box;
`;

export const HMGDataFade = styled.div`
  ${slideCss}
`;
