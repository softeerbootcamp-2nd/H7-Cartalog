import styled from 'styled-components';

export const Logo = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  gap: 20px;
`;

export const Line = styled.div`
  width: 1px;
  height: 13px;
  background-color: ${({ theme }) => theme.color.gray['400']};
`;

export const CarType = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
`;

export const Type = styled.div`
  color: ${({ theme }) => theme.color.gray['800']};
  font: ${({ theme }) => theme.font.textKR.Medium14};
`;
