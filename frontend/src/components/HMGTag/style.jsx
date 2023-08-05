import styled from 'styled-components';

export const HMGTag = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70px;
  background-color: ${({ theme }) => theme.color.activeBlue2};

  ${({ type }) => {
    if (type === 'tag20') return `height: 20px`;
    if (type === 'tag32') return `height: 32px`;
  }}
`;

export const Text = styled.h2`
  color: ${({ theme }) => theme.color.white};
  font: ${({ theme }) => theme.font.head.BoldH5};
`;
