import styled from 'styled-components';

export const NextButton = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 340px;
  gap: 8px;
`;

export const Estimate = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 8px;
`;

export const TotalPrice = styled.div`
  display: flex;
  align-items: center;
  gap: 8px;
`;

export const TotalPriceText = styled.h2`
  font: ${({ theme }) => theme.font.headKR.Regular12};
  color: ${({ theme }) => theme.color.gray['700']};
  font-display: swap;
`;

export const TotalPriceNumber = styled.h2`
  font: ${({ theme }) => theme.font.headKR.Medium24};
  color: ${({ theme }) => theme.color.primary['500']};
  font-display: swap;
`;
