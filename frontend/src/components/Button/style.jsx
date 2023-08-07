import styled from 'styled-components';

const buttonByType = {
  buttonA: `
    width: 850px;
    height: 52px;
  `,
  buttonB: `
    width: 850px;
    height: 52px;
  `,
  buttonC: `
    width: 220px;
    height: 36px;
    border-radius: 2px;
  `,
  buttonD: `
    width: 340px;
    height: 44px;
    border-radius: 4px;
  `,
};

export const Button = styled.button`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  transition: all 0.2s ease-in;
  ${({ type }) => buttonByType[type] || buttonByType.default};
  background-color: ${({ $state, theme }) => {
    switch ($state) {
      case 'active':
        return theme.color.primary['700'];
      case 'inactive':
        return theme.color.gray['300'];
      default:
        throw new Error('active || inactive');
    }
  }};

  &:hover {
    background-color: ${({ theme }) => theme.color.primary['500']};
  }
`;

export const SubTitle = styled.h2`
  color: ${({ theme }) => theme.color.gray['100']};
  font: ${({ theme }) => theme.font.textKR.Regular12};
`;

export const MainTitle = styled.h1`
  color: ${({ theme }) => theme.color.white};
  font: ${({ type, theme }) =>
    type === 'buttonC' ? theme.font.textKR.Medium12 : theme.font.textKR.Medium16};
`;
