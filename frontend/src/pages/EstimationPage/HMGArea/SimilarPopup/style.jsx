import { styled } from 'styled-components';
import { FadeIn, FadeInWithTransform } from '../../../../styles/GlobalStyle';

export const SimilarPopup = styled.div`
  ${FadeInWithTransform}
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
  width: 850px;
  height: 520px;
  border-radius: 8px;
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

export const PopupWrapper = styled.div`
  margin-left: 40px;
`;

export const Header = styled.div`
  display: flex;
  margin-top: 57px;
`;

export const InfoWrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 762px;
`;

export const Info = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export const Title = styled.div`
  font: ${({ theme }) => theme.font.headKR.Medium16};
  color: ${({ theme }) => theme.color.gray['800']};
  font-display: swap;
  white-space: pre-wrap;

  & > span {
    color: ${({ theme }) => theme.color.activeBlue2};
  }
`;

export const TitleInfo = styled.div`
  font: ${({ theme }) => theme.font.headKR.Regular12};
  color: ${({ theme }) => theme.color.primary.default};
  font-display: swap;
  white-space: pre-wrap;
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
  flex-shrink: 0;
  margin-top: 25px;
`;

export const Footer = styled.div`
  margin-top: 41px;
`;
