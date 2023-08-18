import styled from 'styled-components';

export const HMGData = styled.div`
  display: flex;
  flex-direction: row;

  align-items: center;
  margin-top: 16px;
`;

export const Divide = styled.div`
  height: 41px;
  width: 1px;
  margin: 0 24px;
  background-color: ${({ theme }) => theme.color.gray['200']};
`;
