import { keyframes, styled } from 'styled-components';
import { EASE_OUT_CUBIC } from '../../constants';

const displayAnimation = keyframes`
  to {
    visibility: hidden;
  }
`;

export const PriceStaticBar = styled.div.attrs(({ theme, $over }) => ({
  style: {
    backgroundColor: $over ? '#000b19e5' : theme.color.primary['700'],
  },
}))`
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 76px;
  left: 50%;
  transform: translateX(-50%);
  width: 343px;
  padding: 4px 16px;
  border-radius: 10px;
  backdrop-filter: blur(3px);
  user-select: none;
  transition:
    background-color 0.5s ease,
    transform 0.5s ${EASE_OUT_CUBIC},
    opacity 0.5s ${EASE_OUT_CUBIC};
  overflow: hidden;

  &.over {
    background-color: #000b19e5;
  }

  & svg.open {
    transform: rotate(-180deg);
  }

  &.hidden {
    opacity: 0;
    transform: translateX(-50%) translateY(5%);
    animation: ${displayAnimation} 0.5s step-end forwards;
  }
`;

export const CollapsedArea = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 9px;
  width: 100%;
  height: 32px;
  cursor: pointer;

  & > svg {
    fill: ${({ theme }) => theme.color.gray['50']};
    width: 24px;
    height: 24px;
    transition: transform 0.2s ease;
  }
`;

export const Title = styled.div`
  font: ${({ theme }) => theme.font.headKR.Medium14};
  color: ${({ theme }) => theme.color.gray['50']};
  font-display: swap;
`;

export const ExpandedArea = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: stretch;
  height: 38px;
  transition:
    visibility 0.2s ease,
    height 0.2s ease,
    opacity 0.2s ease;

  &:not(.open) {
    visibility: hidden;
    opacity: 0;
    height: 0;
  }
`;
