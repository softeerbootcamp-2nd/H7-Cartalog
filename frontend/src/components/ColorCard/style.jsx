import { styled } from 'styled-components';
import { CardCss } from '../../styles/GlobalStyle';

export const ColorCard = styled.button`
  ${CardCss}
  display: flex;
  width: 244px;
  height: 110px;
  padding: 0;

  &:hover {
    border-color: ${({ theme }) => theme.color.activeBlue};
  }

  &.selected {
    border-color: ${({ theme }) => theme.color.activeBlue};
    background-color: ${({ theme }) => theme.color.cardBG};
  }
`;

export const ColorPreview = styled.div`
  flex-shrink: 0;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 69px;
  height: 100%;
`;

export const ColorInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  padding: 14px 16px;
  box-sizing: border-box;
`;

export const UpperInfo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

export const LowerInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
`;
