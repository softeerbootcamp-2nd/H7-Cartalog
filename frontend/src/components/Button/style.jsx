import styled from 'styled-components';

export const Button = styled.button`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: ${({ state, theme }) => {
    switch (state) {
      case 'active':
        return ` ${theme.color.primary['700']}`;
      case 'hover':
        return `${theme.color.primary['500']}`;
      case 'click':
        return `${theme.color.primary['800']}`;
      case 'inactive':
        return `${theme.color.gray['300']}`;
      default:
        return `${theme.color.primary['700']}`;
    }
  }};

  ${({ type }) => {
    switch (type) {
      case 'buttonA' || 'buttonB':
        return `
        width: 850px;
        height: 52px;
        `;
      case 'buttonC':
        return `
        width: 220px;
        height: 36px;
        border-radius: 2px;
        `;
      case 'buttonD':
        return `
        width: 340px;
        height: 44px;
        border-radius: 4px;
        `;
      default:
        return `
        width: 220px;
        height: 36px;
        border-radius: 2px;
        `;
    }
  }};
`;

export const SubTitle = styled.h2`
  color: ${({ theme }) => theme.color.gray['100']};
  font: ${({ theme }) => theme.font.textKR.Regular12};
`;

export const MainTitle = styled.h1`
  color: ${({ theme }) => theme.color.white};
  font: ${({ type, theme }) => {
    if (type === 'buttonC') return `${theme.font.textKR.Medium12}`;
    return `${theme.font.textKR.Medium16}`;
  }};
`;
