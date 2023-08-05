import { styled } from 'styled-components';

export const SliderMark = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  font: ${({ theme }) => theme.font.textKR.Medium10};
  color: ${({ theme }) => theme.color.primary['200']};
`;
