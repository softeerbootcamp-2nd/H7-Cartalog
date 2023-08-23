import { styled } from 'styled-components';
import { EASE_OUT_CUBIC } from '../../constants';

// eslint-disable-next-line import/prefer-default-export
export const PriceCompareBar = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  position: relative;
  width: 342px;
  height: 120px;
  padding: 10px 16px;
  border-radius: 10px;
  background: ${({ theme }) => theme.color.primary['700']};
`;

export const Info = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

export const Title = styled.div`
  font: ${({ theme }) => theme.font.headKR.Medium14};
  color: ${({ theme }) => theme.color.gray['50']};
  font-display: swap;
`;

export const Description = styled.div`
  /* width: 218px; */
  text-align: right;
  font: ${({ theme }) => theme.font.textKR.Regular12};
  color: ${({ theme }) => theme.color.gray['50']};
  font-display: swap;

  & > span {
    font: ${({ theme }) => theme.font.textKR.Medium12};
    color: ${({ theme }) => theme.color.activeBlue};
    transition: color 0.5s ease;
    font-display: swap;

    &.over {
      color: ${({ theme }) => theme.color.sand};
    }
  }
`;

export const Identifiers = styled.div`
  display: flex;
  justify-content: center;
  gap: 16px;
`;

export const Identifier = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font: ${({ theme }) => theme.font.textKR.Medium10};
  color: ${({ theme, $active }) => ($active ? theme.color.activeBlue : theme.color.white)};

  &::before {
    content: '';
    display: block;
    width: 8px;
    height: 8px;
    margin-right: 8px;
    border-radius: 50%;
    background-color: ${({ theme, $active }) =>
      $active ? theme.color.activeBlue : theme.color.white};
  }
`;

export const BarArea = styled.div`
  display: flex;
  align-items: center;
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  padding: 0 16px;
  box-sizing: border-box;
`;

export const Bar = styled.div`
  display: flex;
  width: 100%;
  height: 6px;
  position: relative;
  border-radius: 3px;
  background-color: ${({ theme }) => theme.color.primary['400']};
`;

export const TextWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  position: absolute;
  top: 10px;
  width: 100%;

  & > div {
    font: ${({ theme }) => theme.font.textKR.Medium10};
    color: ${({ theme }) => theme.color.primary['200']};
  }
`;

export const Pin = styled.div`
  position: absolute;
  top: -6px;
  left: 0;
  width: 2px;
  height: 18px;
  border-radius: 1px;
  background-color: ${({ theme, $active }) =>
    $active ? theme.color.activeBlue : theme.color.white};
  transition: transform 0.2s ${EASE_OUT_CUBIC};

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
