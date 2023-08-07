import styled from 'styled-components';

export const RoundButton = styled.button`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const Title = styled.div`
  font: ${({ theme }) => theme.font.textKR.Medium14};
  transition: all 0.2s ease-in;

  ${({ $state, type, theme }) => {
    if (type === 'price') {
      return `
        padding: 6px 16px;    
        border-radius: 30px;
        border: 1px solid ${theme.color.primary['300']};
        color: ${theme.color.primary['500']};
        background-color: ${theme.color.white};
        `;
    }
    if (type === 'option') {
      switch ($state) {
        case 'active':
          return `
            padding: 6px 20px;
            border-radius: 20px;
            border: 1px solid ${theme.color.skyBlue};
            color: ${theme.color.primary['500']};
            background-color: ${theme.color.skyBlue}4c
        `;
        case 'inactive':
          return `
            padding: 6px 20px;
            border-radius: 20px;
            border: 1px solid ${theme.color.gray['200']};
            color: ${theme.color.gray['600']};
            background-color: ${theme.color.white};
        `;
        default:
          throw new Error('active || inactive');
      }
    }
  }};

  &:hover {
    ${({ $state, type, theme }) => {
      if (type === 'price' && $state === 'active') {
        return `background-color: ${theme.color.skyBlue}4c`;
      }
      if (type === 'option' && $state === 'active') {
        return `background-color: ${theme.color.white}`;
      }
    }}
  }
`;
