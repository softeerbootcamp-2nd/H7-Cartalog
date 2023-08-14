import { keyframes, styled } from 'styled-components';
import { EASE_OUT_CUBIC } from '../../constants';

export const Chart = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;

  & div {
    font: ${({ theme }) => theme.font.textKR.Medium14};
    color: ${({ theme }) => theme.color.gray['300']};
  }

  &.active div {
    color: ${({ theme }) => theme.color.activeBlue};
  }
`;

const BarAnimation = keyframes`
  from {
    transform: scaleY(0);
  }

  to {
    transform: scaleY(1);
  }
`;

export const Bar = styled.div`
  position: relative;
  width: 14px;
  height: ${({ $height }) => `${$height}px`};
  background-color: ${({ theme }) => theme.color.gray['200']};
  transform: scaleY(0);
  transform-origin: bottom;
  animation: ${BarAnimation} 0.5s ${EASE_OUT_CUBIC} forwards;

  .active & {
    background-color: ${({ theme }) => theme.color.activeBlue};
  }
`;
