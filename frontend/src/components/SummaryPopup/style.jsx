import { styled } from 'styled-components';
import { FadeIn, FadeInWithTransform } from '../../styles/GlobalStyle';

export const SummaryPopup = styled.div`
  ${FadeInWithTransform}
  display: flex;
  flex-direction: column;
  gap: 8px;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
  width: 850px;
  height: 520px;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.color.white};
  overflow: hidden;
`;

export const Overlay = styled.div`
  ${FadeIn}
  position: fixed;
  top: 0;
  left: 0;
  z-index: 999;
  width: 100%;
  height: 100%;
  background-color: #1f1f1fb2;
  backdrop-filter: blur(6px);
`;

export const Header = styled.div`
  display: flex;
  position: relative;
`;

export const Title = styled.div`
  width: 100%;
  padding-top: 20px;
  font: ${({ theme }) => theme.font.headKR.Medium18};
  text-align: center;
`;

export const CloseButton = styled.button`
  position: absolute;
  top: 20px;
  right: 20px;

  & > svg {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 24px;
    height: 24px;
    fill: ${({ theme }) => theme.color.gray['800']};
  }
`;

export const Contents = styled.div`
  display: flex;
  flex: 1;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
`;

export const LeftArea = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 20px;

  & > img {
    width: 418px;
    height: 212px;
    object-fit: cover;
  }
`;

export const PopupButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 52px;
  background-color: ${({ theme }) => theme.color.primary['700']};
  color: ${({ theme }) => theme.color.white};
`;
