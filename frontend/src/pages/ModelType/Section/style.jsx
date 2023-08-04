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
  width: 210px;
  gap: 4px;
`;

export const SubTitle = styled.h2`
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.textKR.Regular14};
`;

export const MainTitle = styled.h1`
  color: ${({ theme }) => theme.color.primary['700']};
  font: ${({ theme }) => theme.font.headKR.Bold32};
`;

export const InfoTitle = styled.h4`
  margin-top: 4px;
  word-break: keep-all;
  color: ${({ theme }) => theme.color.gray['800']};
  font: ${({ theme }) => theme.font.textKR.Regular12};
`;

export const Data = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 32px;
`;
