import styled from 'styled-components';

export const PickTitle = styled.div`
  margin-top: 16px;
`;

export const MainTitle = styled.h1`
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Medium16};
  font-display: swap;
`;
