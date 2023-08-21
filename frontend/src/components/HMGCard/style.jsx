import { styled } from 'styled-components';

export const HMGCard = styled.div`
  width: 347px;
  height: fit-content;
  background: ${({ theme }) => theme.color.gray['50']};
`;

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px 16px;
`;

export const Title = styled.div`
  width: 100%;
  font: ${({ theme }) => theme.font.headKR.Medium16};
  color: ${({ theme }) => theme.color.gray['900']};
  font-display: swap;

  & > span {
    color: ${({ theme }) => theme.color.activeBlue};
  }
`;

export const Description = styled.div`
  width: 100%;
  margin-top: 10px;
  font: ${({ theme }) => theme.font.textKR.Regular14};
  color: ${({ theme }) => theme.color.gray['600']};
  word-break: keep-all;
  font-display: swap;
`;

export const Divider = styled.div`
  width: 314px;
  height: 1px;
  background-color: ${({ theme }) => theme.color.gray['100']};
  margin: 15px 0;
`;
