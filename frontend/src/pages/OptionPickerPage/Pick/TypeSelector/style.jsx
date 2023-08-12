import { styled } from 'styled-components';

export const TypeSelector = styled.nav`
  display: flex;
  gap: 24px;
`;

export const SelectorItem = styled.button`
  font: ${({ theme }) => theme.font.headKR.Medium16};
  color: ${({ theme }) => theme.color.gray['200']};

  &.selected {
    color: ${({ theme }) => theme.color.gray['900']};
  }
`;
