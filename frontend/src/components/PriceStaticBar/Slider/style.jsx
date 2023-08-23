import { styled } from 'styled-components';
import ThumbSvg from './thumb.svg';

export const SliderContainer = styled.div`
  position: relative;
  width: ${({ width }) => width}px;
  height: 20px;

  .background {
    position: absolute;
    top: calc(50% - 3px);
    width: 100%;
    height: 6px;
    border-radius: 4px;
    background-color: ${({ theme }) => theme.color.primary['800']};
    transition: background-color 0.2s ease;

    &.over {
      background-color: #414344;
    }
  }

  .fill {
    position: absolute;
    top: calc(50% - 3px);
    height: 6px;
    border-radius: 4px;
    background-color: ${({ theme }) => theme.color.white};
  }
`;

export const Slider = styled.input`
  appearance: none;
  display: block;
  position: absolute;
  top: 0px;
  width: 100%;
  height: 100%;
  margin: 0;
  background-color: transparent;
  border-radius: 4px;
  cursor: pointer;

  &:focus {
    outline: none;
  }

  &::-webkit-slider-thumb {
    appearance: none;
    position: relative;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    background-image: url(${ThumbSvg});
    background-color: ${({ theme }) => theme.color.primary['400']};
    transition: background-color 0.5s ease;
  }

  &.over::-webkit-slider-thumb {
    background-color: ${({ theme }) => theme.color.sand};
  }
`;

export const Pin = styled.div`
  position: relative;
  z-index: 1;
  width: 2px;
  height: 18px;
  border-radius: 2px;
  background-color: ${({ theme }) => theme.color.activeBlue};
  transition:
    left 0.2s ease,
    background-color 0.5s ease;

  &.over {
    background-color: ${({ theme }) => theme.color.sand};
  }

  &::before {
    content: '';
    position: absolute;
    top: -4px;
    left: -3px;
    width: 8px;
    height: 8px;
    border-radius: 50%;
    background-color: inherit;
  }
`;
