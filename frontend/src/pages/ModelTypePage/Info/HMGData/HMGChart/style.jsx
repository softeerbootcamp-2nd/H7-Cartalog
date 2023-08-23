import styled, { keyframes } from 'styled-components';
import { EASE_OUT_CUBIC } from '../../../../../constants';

export const HMGChart = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Title = styled.h4`
  color: ${({ theme }) => theme.color.gray['600']};
  font: ${({ theme }) => theme.font.textKR.Medium10};
  font-display: swap;
`;

export const Output = styled.h4`
  margin-top: 8px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Regular28};
  font-display: swap;
`;

export const Chart = styled.div`
  position: relative;
  width: 200px;
  height: 4px;
  margin-top: 4px;
  background: ${({ theme }) => theme.color.gray['200']};
`;

const BarAnimation = keyframes`
  from {
    transform: scaleX(0);
  }
  to {
    transform: scaleX(1);
  }
`;

export const Bar = styled.div`
  position: absolute;
  left: 0;
  width: 0;
  height: 4px;
  background-color: ${({ theme }) => theme.color.activeBlue2};
  transform-origin: left;
  animation: ${BarAnimation} 0.5s ${EASE_OUT_CUBIC} forwards;
  transition: width 0.5s ${EASE_OUT_CUBIC};
`;
