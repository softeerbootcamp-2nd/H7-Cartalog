import styled from 'styled-components';

export const Footer = styled.div`
  display: flex;
  flex-direction: column;
  width: 1024px;
  margin: 0 auto;
`;

export const ImageViewButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-bottom: 25px;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  gap: 17px;
`;

export const TotalPrice = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-bottom: 15px;
  gap: 8px;
`;

export const TotalPriceText = styled.h2`
  font: ${({ theme }) => theme.font.headKR.Regular12};
  color: ${({ theme }) => theme.color.gray['700']};
`;

export const TotalPriceNumber = styled.h2`
  font: ${({ theme }) => theme.font.headKR.Medium24};
  color: ${({ theme }) => theme.color.primary['500']};
`;
