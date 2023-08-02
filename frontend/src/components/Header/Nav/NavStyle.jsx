import { styled } from 'styled-components';

export const Nav = styled.nav`
  display: flex;
  margin-bottom: 6px;
`;

export const Stage = styled.ul`
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  gap: 40px;
`;

export const Step = styled.li`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 52px;
  height: 22px;
  color: ${({ theme }) => theme.color.gray['200']};
  font: ${({ theme }) => theme.font.TextKRMedium14};
`;
