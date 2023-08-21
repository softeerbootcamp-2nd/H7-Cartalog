import { styled } from 'styled-components';

export const Selector = styled.div`
  display: flex;
`;

export const Bar = styled.div`
  position: absolute;
  left: 0;
  bottom: 6px;
  width: 100%;
  height: 2px;
  background-color: ${({ theme }) => theme.color.gray['800']};
  border-radius: 2px;
  transform: scale(0);
  transform-origin: left center;
  transition: transform 0.2s ease;
`;

export const SelectorList = styled.ul`
  display: flex;
  position: relative;
  align-items: center;
  gap: 16px;
  width: 408px;
  overflow: hidden;
`;

export const SelectorItem = styled.li`
  flex-shrink: 0;
  margin-bottom: 6px;
  font: ${({ theme }) => theme.font.textKR.Regular14};
  color: ${({ theme }) => theme.color.gray['400']};
  font-display: swap;
  cursor: pointer;
  transition: color 0.2s ease;

  &.selected {
    font: ${({ theme }) => theme.font.textKR.Medium14};
    color: ${({ theme }) => theme.color.gray['800']};
    font-display: swap;
  }
`;

export const ArrowButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 8px;

  & > svg {
    fill: ${({ theme }) => theme.color.gray['200']};
  }

  &.active > svg {
    fill: ${({ theme }) => theme.color.gray['600']};
  }
`;
