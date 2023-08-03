import styled from 'styled-components';

export const HMGData = styled.div`
  display: flex;
  flex-direction: row;
  margin-top: 16px;
  gap: 52px;
`;

export const HMGDataItem = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
`;

export const Title = styled.h4`
  height: 36px;
  word-break: keep-all;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.textKR.Regular10};
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
