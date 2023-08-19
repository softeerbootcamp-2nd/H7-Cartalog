import styled from 'styled-components';
import { SlideFromRight } from '../../styles/GlobalStyle';

export const Title = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 72px;
`;

export const SubTitle = styled.h2`
  ${({ type, theme }) => {
    if (type === 'light') return `color: ${theme.color.white}`;
    if (type === 'dark') return `color: ${theme.color.gray['900']}`;
  }};
  font: ${({ theme }) => theme.font.textKR.Regular14};
  ${SlideFromRight};
`;

export const MainTitle = styled.h1`
  margin-top: 4px;
  ${({ type, theme }) => {
    if (type === 'light') return `color: ${theme.color.white}`;
    if (type === 'dark') return `color: ${theme.color.primary['700']}`;
  }};
  font: ${({ theme }) => theme.font.head.Bold32};
  ${SlideFromRight};
`;

export const Info = styled.h2`
  margin-top: 8px;
  white-space: pre-line;
  word-break: keep-all;
  ${({ type, theme }) => {
    if (type === 'light') return `color: ${theme.color.white}`;
    if (type === 'dark') return `color: ${theme.color.gray['800']}`;
  }};
  font: ${({ theme }) => theme.font.textKR.Regular12};
  ${SlideFromRight};
`;
