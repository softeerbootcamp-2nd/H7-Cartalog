import styled from 'styled-components';
import { SlideFromRight } from '../../styles/GlobalStyle';

export const Title = styled.div`
  display: flex;
  flex-direction: column;
  width: 488px;
  height: 100%;
  margin-top: 72px;
`;

export const SubTitle = styled.h2`
  ${({ type, theme }) => {
    if (type === 'light') return `color: ${theme.color.white}`;
    if (type === 'dark') return `color: ${theme.color.gray['900']}`;
  }};
  font: ${({ theme }) => theme.font.textKR.Regular14};
  font-display: swap;
  ${SlideFromRight};
`;

export const MainTitle = styled.h1`
  margin-top: 4px;
  ${({ type, theme }) => {
    switch (type) {
      case 'light':
        return `color: ${theme.color.white}`;
      case 'dark':
        return `color: ${theme.color.primary['700']}`;
      default:
        throw new Error('light || dark');
    }
  }};
  font: ${({ theme }) => theme.font.headKR.Bold32};
  font-display: swap;
  ${SlideFromRight};
`;

export const Info = styled.div`
  height: 100%;
  margin-top: 8px;
  white-space: pre-line;
  word-break: keep-all;
  ${({ type, theme }) => {
    switch (type) {
      case 'light':
        return `color: ${theme.color.white}`;
      case 'dark':
        return `color: ${theme.color.gray['800']}`;
      default:
        throw new Error('light || dark');
    }
  }};
  font: ${({ theme }) => theme.font.textKR.Regular12};
  font-display: swap;
  ${SlideFromRight};
`;
