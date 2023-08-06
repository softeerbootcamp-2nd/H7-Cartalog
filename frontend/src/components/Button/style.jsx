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
  background-color: ${({ $state, theme }) => {
    switch ($state) {
      case 'active':
        return theme.color.primary['700'];
      case 'hover':
        return theme.color.primary['500'];
      case 'state':
        return theme.color.primary['800'];
      case 'inactive':
        return theme.color.gray['300'];
      default:
        return theme.color.primary['700'];
    }
  }};

  &:hover {
    background-color: ${({ theme }) => theme.color.primary['500']};
    transition: all 0.1s ease-in;
  }
  &:not(:hover) {
    transition: all 0.1s ease-out;
  }

  ${({ type }) => buttonByType[type] || buttonByType.default};
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
