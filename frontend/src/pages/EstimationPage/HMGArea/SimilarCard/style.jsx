import { styled } from 'styled-components';

export const ChartWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  width: 100%;

  &.stop * {
    animation: none;
  }

  ${({ $n }) =>
    [...Array($n).keys()]
      .map(
        (i) => `& > div:nth-child(${i + 1}) > div {
          animation-delay: ${0.1 * i}s;
      }`,
      )
      .join('')}
`;

export const Button = styled.button`
  width: 311px;
  height: 47px;
  border-radius: 2px;
  margin-top: 12px;
  background-color: ${({ theme }) => theme.color.skyBlue}4c;
  color: ${({ theme }) => theme.color.primary.default};
  font: ${({ theme }) => theme.font.textKR.Medium14};
`;
