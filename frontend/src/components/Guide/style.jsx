import styled from 'styled-components';
import { FadeIn } from '../../styles/GlobalStyle';
import { ReactComponent as CancelSVG } from '../../../assets/icons/cancel.svg';

export const LayoutWrapper = styled.div`
  position: relative;
  width: 1280px;
  margin: 0 auto;
  border: 1px solid red;
`;

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

export const SampleInfo = styled.div`
  position: absolute;
  top: 230px;
  left: 108px;
  z-index: 1002;
  background-color: ${({ theme }) => theme.color.white};
  padding: 0 20px;
`;

export const PopupWrapper = styled.div`
  display: flex;
  flex-direction: row;
  position: absolute;
  top: 230px;
  left: 460px;
  z-index: 1002;
  width: 324px;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.color.white};

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: -20px;
    width: 0;
    height: 0;
    border-bottom: 10px solid transparent;
    border-top: 10px solid transparent;
    border-left: 10px solid transparent;
    border-right: 10px solid rgb(227, 238, 248);
    margin-top: 35px;
  }
`;

export const Popup = styled.div`
  display: flex;
  flex-direction: column;
  z-index: 1002;
  width: 100%;
  height: 100%;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.color.skyBlueCardBG};
  overflow: hidden;
  padding: 25px;
  gap: 10px;
`;

export const PopupContent = styled.div`
  display: flex;
  flex-direction: column;
  font: ${({ theme }) => theme.font.textKR.Medium14};
  color: ${({ theme }) => theme.color.gray['900']};

  & > div {
    display: flex;
  }
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const Highlight = styled.div`
  color: ${({ theme }) => theme.color.activeBlue};
`;

export const Divide = styled.div`
  width: 36px;
  height: 1px;
  background-color: ${({ theme }) => theme.color.primary['200']};
`;

export const InfoContent = styled.div`
  width: 266px;
  height: 72px;
  font: ${({ theme }) => theme.font.textKR.Regular12};
  color: ${({ theme }) => theme.color.primary.default};
`;

export const CancelButton = styled.button`
  width: 24px;
  height: 24px;
`;

export const CancelIcon = styled(CancelSVG)`
  fill: ${({ theme }) => theme.color.gray['800']};
  cursor: pointer;
  width: 24px;
  height: 24px;
`;
