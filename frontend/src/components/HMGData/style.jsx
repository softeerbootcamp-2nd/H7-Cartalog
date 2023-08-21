import styled, { css } from 'styled-components';
import { SlideFromRight } from '../../styles/GlobalStyle';

export const HMGData = styled.div`
  ${SlideFromRight};
  display: flex;
  flex-direction: row;
  margin-top: 16px;

  ${({ $type }) => {
    switch ($type) {
      case 'option':
        return `gap: 80px;`;
      default:
        return `gap: 52px;`;
    }
  }};
`;

export const Item = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  position: relative;

  ${({ $type }) => {
    switch ($type) {
      case 'option':
        return `width: 152px;`;
      default:
        return `width: 60px;`;
    }
  }};
`;

const optionTitleCss = css`
  font: ${({ theme }) => theme.font.textKR.Medium12};
  font-display: swap;
`;

const defaultTitleCss = css`
  height: 35px;
  font: ${({ theme }) => theme.font.textKR.Regular10};
  font-display: swap;

  &:hover {
    position: absolute;
    top: 0;
    width: 100%;
    height: auto;
    overflow: visible;
    border-radius: 2px;
    background-color: white;
  }
`;

export const Title = styled.div`
  word-break: keep-all;
  overflow: hidden;
  color: ${({ theme }) => theme.color.gray['900']};

  ${({ $type }) => {
    switch ($type) {
      case 'option':
        return optionTitleCss;
      default:
        return defaultTitleCss;
    }
  }};
`;

export const Divide = styled.div`
  height: 1px;
  margin: 4px 0;
  background-color: ${({ theme }) => theme.color.gray['400']};
`;

export const Count = styled.div`
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Regular24};
  font-display: swap;
`;

export const Per = styled.div`
  color: ${({ theme }) => theme.color.gray['600']};
  font: ${({ theme }) => theme.font.textKR.Regular10};
  font-display: swap;
`;
