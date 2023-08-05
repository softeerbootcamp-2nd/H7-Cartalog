import { styled } from 'styled-components';

export const Description = styled.div`
  width: 218px;
  text-align: right;
  font: ${({ theme }) => theme.font.textKR.Regular12};
  color: ${({ theme }) => theme.color.gray['50']};

  & > span {
    font: ${({ theme }) => theme.font.textKR.Medium12};
    color: ${({ theme }) => theme.color.activeBlue};
    transition: color 0.5s ease;

    &.over {
      color: ${({ theme }) => theme.color.sand};
    }
  }
`;
