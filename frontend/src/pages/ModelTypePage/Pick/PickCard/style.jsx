import styled from 'styled-components';

export const PickCard = styled.div`
  display: flex;
  flex-direction: column;
`;

export const SelectCard = styled.div`
  display: flex;
  padding: 4px;
  gap: 5px;
`;

export const TypeCardName = styled.div`
  margin-top: 4px;
  font: ${({ theme }) => theme.font.headKR.Medium14};
  color: ${({ theme }) => theme.color.gray['600']};
`;
