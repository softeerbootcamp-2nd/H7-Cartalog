import styled, { css } from 'styled-components';
import { FadeIn, FadeInWithTransform } from '../../styles/GlobalStyle';

export const Overlay = styled.div`
  ${FadeIn}
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1001;
  width: 100%;
  height: 100%;
  background-color: #1f1f1fb2;
  backdrop-filter: blur(6px);
`;

export const Popup = styled.div`
  ${FadeInWithTransform}
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1002;
  width: 335px;
  height: 200px;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.color.white};
  overflow: hidden;
`;

export const PopupContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  text-align: center;
  flex: 1;
  padding: 20px;
  white-space: pre-line;
  font: ${({ theme }) => theme.font.textKR.Medium14};
  color: ${({ theme }) => theme.color.gray['900']};
  font-display: swap;
`;

export const PopupActions = styled.div`
  display: flex;
  justify-content: stretch;
  align-items: center;
  height: 52px;
`;

const PopupButtonStyles = css`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  font: ${({ theme }) => theme.font.headKR.Medium16};
  user-select: none;
  font-display: swap;
`;

export const SecondaryPopupButton = styled.button`
  ${PopupButtonStyles}
  background-color: ${({ theme }) => theme.color.gray['300']};
  color: ${({ theme }) => theme.color.white};
`;

export const PopupButton = styled.button`
  ${PopupButtonStyles}
  background-color: ${({ theme }) => theme.color.primary['700']};
  color: ${({ theme }) => theme.color.gray['100']};
`;
