import { styled } from 'styled-components';
import { SlideFromBottom } from '../../../../styles/GlobalStyle';
import { EASE_OUT_CUBIC } from '../../../../constants';

// eslint-disable-next-line import/prefer-default-export
export const CarImage = styled.div`
  ${SlideFromBottom}
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  animation-delay: 1.5s;
  gap: 10px;

  & > img {
    position: absolute;
    width: 760px;
    height: 360px;
    object-fit: cover;
    opacity: 0;
    transition:
      opacity 0.5s ${EASE_OUT_CUBIC},
      width 0.5s ${EASE_OUT_CUBIC},
      height 0.5s ${EASE_OUT_CUBIC};

    &.visible {
      opacity: 1;
    }
  }

  &.expanded > img {
    width: 100%;
    height: 100%;
  }
`;
