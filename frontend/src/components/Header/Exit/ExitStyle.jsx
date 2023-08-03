import styled from 'styled-components';

export const Exit = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  gap: 8px;
`;

export const Text = styled.span`
  color: ${({ theme }) => theme.color.gray['800']};
  font: ${({ theme }) => theme.font.textKR.Medium14};
`;
