import styled from 'styled-components';

export const HMGTag = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70px;
  background-color: ${({ theme }) => theme.color.activeBlue2};

  ${(type) => {
    switch (type) {
      case 'Tag1':
        return `
        height: 20px;
        `;
      case 'Tag2':
        return `
        height: 30px;
        `;
      default:
        return `
        height: 20px;
        `;
    }
  }};
`;

export const Text = styled.h2`
  color: ${({ theme }) => theme.color.white};
  font: ${({ theme }) => theme.font.head.BoldH5};
`;
