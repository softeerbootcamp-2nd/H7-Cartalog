import styled from 'styled-components';

export const HMGUnit = styled.div``;

export const Detail = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export const DetailTitle = styled.p`
  display: flex;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.textKR.Regular14};
  font-display: swap;
`;

export const DetailUnit = styled.div`
  display: flex;
  margin-top: 8px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Regular28};
  font-display: swap;
`;
