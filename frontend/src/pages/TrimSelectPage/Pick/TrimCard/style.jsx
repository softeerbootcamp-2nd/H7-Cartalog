import { styled } from 'styled-components';

export const TrimCard = styled.div`
  display: flex;
  flex-direction: column;
  width: 244px;
  height: 158px;

  border: 1px solid ${({ theme }) => theme.color.gray['200']};
  border-radius: 2px;
  background-color: ${({ theme }) => theme.color.white};
  cursor: pointer;

  &:hover,
  &.active {
    border: 1px solid ${({ theme }) => theme.color.activeBlue};
  }

  &.active {
    background-color: ${({ theme }) => theme.color.cardBG};
  }
`;

export const Trim = styled.div`
  display: flex;
  flex-direction: column;
  margin: 20px 16px 4px;
`;

export const Description = styled.div`
  height: 18px;
  font: ${({ theme }) => theme.font.textKR.Regular12};
  color: ${({ theme }) => theme.color.gray['900']};
  font-display: swap;
`;

export const Name = styled.div`
  height: 28px;
  font: ${({ theme }) => theme.font.headKR.Medium20};
  color: ${({ theme }) => theme.color.gray['900']};
  font-display: swap;
`;

export const Price = styled.div`
  margin: 4px 16px;
  font: ${({ theme }) => theme.font.headKR.Medium18};
  color: ${({ theme }) => theme.color.gray['900']};
  font-display: swap;
`;

export const SelectButton = styled.div`
  margin: 4px 12px;
`;
