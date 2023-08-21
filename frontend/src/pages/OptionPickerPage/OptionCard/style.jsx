import { keyframes, styled } from 'styled-components';
import { CardCss } from '../../../styles/GlobalStyle';

export const OptionCard = styled.button`
  ${CardCss}
  display: flex;
  flex-direction: column;
  position: relative;
  width: 244px;
  height: 278px;
  padding: 0;
  overflow: hidden;
`;

export const OptionImg = styled.div`
  flex-shrink: 1;
  position: relative;
  width: 100%;
  height: 160px;
  overflow: hidden;
  transition: all 0.2s ease;

  & > img {
    width: 100%;
    height: 160px;
    object-fit: cover;
  }
`;

export const HMGTagWrapper = styled.div`
  position: absolute;
  top: 0;
  right: 0;
`;

export const HashTags = styled.div`
  display: flex;
  gap: 8px;
  position: absolute;
  left: 12px;
  bottom: 8px;
  overflow: hidden;

  & > div {
    flex-shrink: 0;
    padding: 2px 6px;
    border-radius: 2px;
    font: ${({ theme }) => theme.font.textKR.Regular12};
    color: ${({ theme }) => theme.color.gray['50']};
    font-display: swap;
    background-color: rgba(117, 117, 117, 0.5);
    backdrop-filter: blur(2px);
  }
`;

export const OptionInfo = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  width: 100%;
  padding: 16px;
  box-sizing: border-box;
  transition: all 0.2s ease;
`;

const scrollAnimation = keyframes`
  /* 0% {
    transform: translateX(0);
  } */
  100% {
    transform: translateX(-100%);
  }
`;

export const UpperInfo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  overflow: hidden;

  & .title {
    width: 100%;
    text-align: left;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
  }

  &:hover .title {
    /* overflow-x: scroll; */
    text-overflow: unset;
    overflow: visible;
    animation: ${scrollAnimation} 5s linear infinite;
  }
`;

export const LowerInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  width: 100%;

  & > label {
    cursor: pointer;
  }

  & .iconWrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 40px;
    height: 40px;
    border-radius: 2px;
    border: 1px solid ${({ theme }) => theme.color.gray['100']};
    background-color: ${({ theme }) => theme.color.white};
    box-sizing: border-box;
    transition: all 0.2s ease;

    .selected & > svg {
      fill: ${({ theme }) => theme.color.gray['200']};
    }

    .checked & {
      border: none;
      background-color: ${({ theme }) => theme.color.activeBlue};

      & > svg {
        fill: ${({ theme }) => theme.color.white};
      }
    }
  }
`;
