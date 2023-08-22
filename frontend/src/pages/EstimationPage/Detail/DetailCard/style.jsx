import { styled } from 'styled-components';

export const DetailCard = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
`;

export const DetailContents = styled.div`
  ${({ $n }) =>
    [...Array($n).keys()]
      .map(
        (i) => `& > div:nth-child(${i + 1}) {
          opacity: 0;
          transition: opacity 0.25s ease ${0.05 * ($n - i - 1)}s;
          
        .expanded & {
          opacity: 1;
          transition: opacity 0.25s ease ${0.05 * i}s;
        }
    }`,
      )
      .join('')}

  height: 0;
  overflow: hidden;
  transition:
    height 0.5s ease 0.1s,
    margin-top 0.5s ease;

  .expanded & {
    transition:
      height 0.5s ease,
      margin-top 0.5s ease;
    height: ${({ $n }) => 76 * $n - 2}px;
  }
`;
