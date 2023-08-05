import styled from 'styled-components';

export const HMGData = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 16px;
`;

export const HMGDataItem = styled.div`
  display: flex;
  flex-direction: column;
  width: 60px;
`;

export const Title = styled.h4`
  display: flex;
  flex-direction: row;
  height: 36px;
  word-break: keep-all;
  color: ${({ theme }) => theme.color.gray['600']};
  font: ${({ theme }) => theme.font.textKR.Medium10};
`;

export const Output = styled.h4`
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Regular28};
`;

export const Divide = styled.div`
  height: 1px;
  background-color: ${({ theme }) => theme.color.gray['400']};
`;

export const Count = styled.div`
  margin-top: 6px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Regular24};
`;

export const Per = styled.div`
  color: ${({ theme }) => theme.color.gray['600']};
  font: ${({ theme }) => theme.font.textKR.Regular10};
`;
