import { styled } from 'styled-components';

export const TypeSelector = styled.nav`
  position: relative;
  display: flex;
  gap: 24px;
  width: fit-content;
  padding-bottom: 2px;
`;

export const Bar = styled.div`
  position: absolute;
  bottom: 0;
  left: 0;
  width: 18px;
  height: 2px;
  border-radius: 1px;
  background-color: ${({ theme }) => theme.color.primary.default};
  transition: transform 0.2s ease;
  transform-origin: left center;
`;

export const SelectorItem = styled.button`
  font: ${({ theme }) => theme.font.headKR.Medium16};
  color: ${({ theme }) => theme.color.gray['200']};

  &.selected {
    color: ${({ theme }) => theme.color.gray['900']};
  }
`;
