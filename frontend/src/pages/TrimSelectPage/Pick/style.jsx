import styled from 'styled-components';

export const Pick = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Title = styled.h1`
  margin-top: 16px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Medium16};
`;

export const Trim = styled.div`
  margin-top: 12px;
  display: flex;
  justify-content: space-between;
`;
