import { styled } from 'styled-components';

export const Summary = styled.div`
  display: flex;
  gap: 50px;
`;

export const TrimData = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Title = styled.div`
  font: ${({ theme }) => theme.font.textKR.Regular12};
  color: ${({ theme }) => theme.color.gray['600']};
  font-display: swap;
`;

export const Name = styled.div`
  font: ${({ theme }) => theme.font.headKR.Bold26};
  color: ${({ theme }) => theme.color.gray['900']};
  font-display: swap;
`;
