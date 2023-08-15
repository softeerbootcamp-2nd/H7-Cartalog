import { styled } from 'styled-components';

// eslint-disable-next-line import/prefer-default-export
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
