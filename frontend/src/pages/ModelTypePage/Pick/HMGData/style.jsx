import styled from 'styled-components';

export const HMGData = styled.div`
  display: flex;
  justify-content: space-between;
  width: 677px;
  height: 114px;
  background-color: ${({ theme }) => theme.color.blueBG};
`;

export const Info = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 46px;
`;

export const InfoText = styled.h1`
  display: flex;
  align-items: center;
  margin-top: 4px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.textKR.Regular14};
`;

export const InfoHighlight = styled.p`
  color: ${({ theme }) => theme.color.activeBlue};
  font: ${({ theme }) => theme.font.textKR.Medium16};
`;

export const Detail = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-right: 70px;
`;

export const Divide = styled.div`
  width: 1px;
  height: 41px;
  margin: 0 32px;
  background-color: ${({ theme }) => theme.color.gray['400']};
`;
