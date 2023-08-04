import styled from 'styled-components';

export const Section = styled.div`
  display: flex;
  flex-direction: row;
  height: 360px;
  margin-top: 60px;
  justify-content: space-between;
`;

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 72px;
`;

export const Title = styled.div`
  display: flex;
  flex-direction: column;
  gap: 4px;
`;

export const SubTitle = styled.h2`
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.textKR.Regular14};
`;

export const MainTitle = styled.h1`
  color: ${({ theme }) => theme.color.primary['700']};
  font: ${({ theme }) => theme.font.head.Bold32};
`;

export const Data = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 32px;
`;

export const Info = styled.div`
  display: flex;
  align-items: center;
  margin-top: 12px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.textKR.Medium12};
`;

export const MainInfo = styled.div`
  color: ${({ theme }) => theme.color.activeBlue};
`;
