import { styled } from 'styled-components';

export const Toggle = styled.div`
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 4px;
  background-color: ${({ theme }) => theme.color.white};
  border: 1px solid ${({ theme }) => theme.color.gray['100']};
  box-sizing: border-box;
  width: 154px;
  height: 44px;
  border-radius: 22px;

  &.big {
    width: 213px;
    height: 48px;
    border-radius: 24px;
  }
`;

export const Selected = styled.div`
  position: absolute;
  top: 3px;
  width: 72px;
  height: 36px;
  border-radius: 18px;
  background-color: ${({ theme }) => theme.color.primary.default};
  transition: transform 0.2s ease;

  .big & {
    width: 100px;
    height: 40px;
    border-radius: 20px;
  }
`;

export const ToggleButton = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1;
  width: 72px;
  height: 36px;
  border-radius: 18px;
  font: ${({ theme }) => theme.font.textKR.Regular14};
  color: ${({ theme }) => theme.color.gray['400']};
  font-display: swap;
  cursor: pointer;
  transition: color 0.2s ease;

  .big & {
    width: 100px;
    height: 40px;
    border-radius: 20px;
  }

  &.checked {
    color: ${({ theme }) => theme.color.gray['50']};
  }
`;
