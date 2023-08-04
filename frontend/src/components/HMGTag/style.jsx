import styled from 'styled-components';

export const HMGTag = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70px;
  background-color: ${({ theme }) => theme.color.activeBlue2};
`;

export const Text = styled.h5`
  color: ${({ theme }) => theme.color.white};
  font: ${({ theme }) => theme.font.head.BoldH5};
`;
