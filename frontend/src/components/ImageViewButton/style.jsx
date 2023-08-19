import { styled } from 'styled-components';

// eslint-disable-next-line import/prefer-default-export
export const ImageViewButton = styled.button`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 76px;
  height: 28px;
  border-radius: 14px;
  font: ${({ theme }) => theme.font.textKR.Medium12};
  color: ${({ theme }) => theme.color.white};
  background-color: rgba(117, 117, 117, 0.5);
  backdrop-filter: blur(2px);
`;
