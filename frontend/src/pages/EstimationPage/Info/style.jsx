import { styled } from 'styled-components';

export const InfoWrapper = styled.div`
  width: 100%;
  height: 95px;
  background-color: ${({ theme }) => theme.color.skyBlueCardBG};
`;

export const Info = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1024px;
  height: 100%;
  margin: 0 auto;

  & > div {
    font: ${({ theme }) => theme.font.textKR.Medium14};
    white-space: pre-line;
    font-display: swap;
  }
`;
