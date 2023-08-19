import styled, { css } from 'styled-components';
import { EASE_OUT_CUBIC } from '../../../constants';

const slideCss = css`
  transform: translateY(120%);
  transition: transform 0.5s ${EASE_OUT_CUBIC};

  &.visible {
    transform: translateY(0);
  }
`;

export const Footer = styled.div`
  ${slideCss}
  width: 100%;
  display: flex;
  flex-direction: column;
  position: fixed;
  bottom: 0;
  z-index: 1;
`;

export const ImageViewButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 1280px;
  padding: 0 128px;
  margin: 0 auto;
  margin-bottom: 15px;
  box-sizing: border-box;
`;

export const FooterBlur = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  height: 114px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(6px);
`;

export const FooterWrapper = styled.div`
  width: 1280px;
  margin: 0 auto;
  padding: 0 128px;
  box-sizing: border-box;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  gap: 17px;
`;

export const TotalPrice = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-bottom: 15px;
  gap: 8px;
`;

export const TotalPriceText = styled.h2`
  font: ${({ theme }) => theme.font.headKR.Regular12};
  color: ${({ theme }) => theme.color.gray['700']};
`;

export const TotalPriceNumber = styled.h2`
  font: ${({ theme }) => theme.font.headKR.Medium24};
  color: ${({ theme }) => theme.color.primary['500']};
`;
