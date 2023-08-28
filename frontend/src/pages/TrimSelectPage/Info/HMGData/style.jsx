import styled from 'styled-components';
import { SlideFromRight } from '../../../../styles/GlobalStyle';

export const HMGData = styled.div`
  display: flex;
  flex-direction: row;
  margin-top: 16px;
  gap: 52px;
  ${SlideFromRight};
`;

export const Item = styled.div`
  display: flex;
  flex-direction: column;
  width: 60px;
`;

export const Title = styled.h4`
  height: 35px;
  word-break: keep-all;
  overflow: hidden;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.textKR.Regular10};
  font-display: swap;
`;

export const Divide = styled.div`
  height: 1px;
  background-color: ${({ theme }) => theme.color.gray['400']};
`;

export const Count = styled.div`
  margin-top: 6px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Regular24};
  font-display: swap;
`;

export const Per = styled.div`
  color: ${({ theme }) => theme.color.gray['600']};
  font: ${({ theme }) => theme.font.textKR.Regular10};
  font-display: swap;
`;
