import { styled } from 'styled-components';

export const Graph = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;

  & > svg {
    stroke: ${({ theme }) => theme.color.gray['200']};
  }
`;

export const PriceInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;

  & > .min {
    text-align: left;
  }

  & > .max {
    text-align: right;
  }
`;

export const Price = styled.div`
  font: ${({ theme }) => theme.font.textKR.Medium16};
  color: ${({ theme }) => theme.color.gray['600']};
  font-display: swap;

  & > span {
    font: ${({ theme }) => theme.font.textKR.Regular16};
    font-display: swap;
  }
`;
