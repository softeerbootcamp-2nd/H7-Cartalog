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

export const Step = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Text = styled.li`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 52px;
  height: 22px;
  color: ${({ theme }) => theme.color.gray['200']};
  font: ${({ theme }) => theme.font.headKR.Medium14};
`;

export const Bar = styled.div`
  margin: 6px 17px -8px 17px;
`;

export const Button = styled.button`
  color: ${({ theme }) => theme.color.gray['200']};
  font: ${({ theme }) => theme.font.headKR.Medium14};

  &.selected {
    color: ${({ theme }) => theme.color.primary.default};
  }
  &.unSelected {
    color: ${({ theme }) => theme.color.primary['200']};
  }
`;
