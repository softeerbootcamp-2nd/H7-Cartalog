import { styled } from 'styled-components';

export const TrimImage = styled.div`
  display: flex;
  gap: 16px;
  cursor: pointer;
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
    user-select: none;
  }

  &.selected > img {
    width: 504px;
  }

  &::after {
    content: '';
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: transparent;
    color: rgba(255, 255, 255, 0.7);
    font: ${({ theme }) => theme.font.headKR.Regular12};
    transition: background-color 0.2s ease;
  }

  &:not(.selected):hover::after {
    content: 'click!';
    background-color: rgba(0, 0, 0, 0.5);
  }
`;
