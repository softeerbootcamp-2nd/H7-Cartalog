import styled from 'styled-components';

const TagByType = {
  tag20: '20px',
  tag32: '32px',
};

export const HMGTag = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 70px;
  background-color: ${({ theme }) => theme.color.activeBlue2};
  height: ${({ type }) => TagByType[type]};
`;

export const Text = styled.h2`
  color: ${({ theme }) => theme.color.white};
  font: ${({ theme }) => theme.font.head.BoldH5};
  font-display: swap;
`;
