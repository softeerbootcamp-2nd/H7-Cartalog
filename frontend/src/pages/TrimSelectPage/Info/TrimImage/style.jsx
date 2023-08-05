import { styled } from 'styled-components';

export const TrimImage = styled.div`
  display: flex;
  gap: 8px;
`;

export const TrimImageItem = styled.div`
  position: relative;
  display: flex;
  height: 360px;

  & > img {
    width: 71px;
    height: 360px;
    object-fit: cover;
    transition: width 0.25s ease;
    z-index: 0;
  }

  &.selected > img {
    width: 504px;
  }

  &:not(.selected):hover::after {
    content: 'click!';
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    color: rgba(255, 255, 255, 0.7);
    font: ${({ theme }) => theme.font.headKR.Regular12};
  }
`;
