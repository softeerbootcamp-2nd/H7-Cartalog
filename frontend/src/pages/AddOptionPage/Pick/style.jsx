import styled from 'styled-components';

export const Pick = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Title = styled.h1`
  margin-top: 16px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Medium16};
`;

export const SelectButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 220px;
  height: 36px;
  margin: 4px 12px 12px;
  border-radius: 2px;
  background-color: ${({ theme }) => theme.color.primary.default};
  font: ${({ theme }) => theme.font.textKR.Medium12};
  color: ${({ theme }) => theme.color.gray['50']};
`;
