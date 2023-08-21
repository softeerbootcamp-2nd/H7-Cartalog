import { styled } from 'styled-components';

export const Detail = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 607px;
  margin-top: 20px;
`;

export const Title = styled.h1`
  font: ${({ theme }) => theme.font.headKR.Medium18};
  color: ${({ theme }) => theme.color.gray['900']};
  font-display: swap;
`;

export const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  width: 100%;
  margin-bottom: 200px;
`;
