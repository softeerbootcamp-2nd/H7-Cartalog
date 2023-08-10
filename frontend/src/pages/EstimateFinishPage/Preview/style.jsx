import { styled } from 'styled-components';

export const Preview = styled.div`
  position: fixed;
  top: 60px;
  width: 100%;
  height: 640px;
  transition: height 0.5s ease;
  background: ${({ theme }) => theme.color.finishGrad};

  &.collapsed {
    height: 342px;
  }
`;

export const CarInfo = styled.div`
  position: relative;
  width: 100%;
  max-width: 1142px;
  height: 100%;
  margin: 0 auto;
  transition: all 0.5s ease;

  .collapsed & {
    height: 342px;

    & > .title {
      left: 70px;
      transform: translateX(-20%) scale(0.6);
    }

    & > .preview {
      top: 72px;
      right: 0px;
      transform: none;

      & > img {
        height: 206px;
      }
    }
  }

  & > .title {
    position: absolute;
    top: 72px;
    left: 50%;
    transform: translateX(-50%);
    white-space: nowrap;

    font: ${({ theme }) => theme.font.headKR.Bold150};
    color: ${({ theme }) => theme.color.white};
    transition: all 0.5s ease;
  }

  & > .preview {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 203px;
    right: 50%;
    transform: translateX(50%);
    transition: all 0.5s ease;

    & > img {
      /* width: 764px;
      height: 360px; */

      height: 412px;
      object-fit: contain;
      transition: all 0.5s ease;
    }
  }
`;
