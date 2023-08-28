import { styled } from 'styled-components';

export const Bar = styled.div`
  display: flex;
  width: 18px;
  height: 2px;
  border-radius: 1px;

  &.selected {
    background-color: ${({ theme }) => theme.color.primary.default};
  }
`;
