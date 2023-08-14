import { styled } from 'styled-components';

export const DetailTitle = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 48px;
  padding: 0 20px;
  background-color: ${({ theme }) => theme.color.blueBG};
`;

export const Title = styled.div`
  font: ${({ theme }) => theme.font.headKR.Medium16};
  color: ${({ theme }) => theme.color.gray['900']};
`;

export const Area = styled.div`
  display: flex;
  gap: 20px;

  & > button {
    width: 24px;
    height: 24px;

    & > svg {
      fill: ${({ theme }) => theme.color.gray['900']};
      transition: transform 0.2s ease;

      &.open {
        transform: rotate(-180deg);
      }
    }
  }
`;

export const Price = styled.div`
  font: ${({ theme }) => theme.font.headKR.Medium14};
  color: ${({ theme }) => theme.color.primary.default};
`;
