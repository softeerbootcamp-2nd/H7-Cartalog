import { styled } from 'styled-components';
import { ReactComponent as CheckSvg } from '../../../assets/icons/check.svg';

export const ColorCard = styled.button`
  display: flex;
  width: 220px;
  height: 110px;
  padding: 0;
  border-radius: 2px;
  border: 1px solid ${({ theme }) => theme.color.gray['200']};
  transition:
    border-color 0.2s ease,
    background-color 0.2s ease;

  &:hover {
    border-color: ${({ theme }) => theme.color.activeBlue};
  }

  &.selected {
    border-color: ${({ theme }) => theme.color.activeBlue};
    background-color: ${({ theme }) => theme.color.cardBG};
  }
`;

export const ColorPreview = styled.div`
  flex-shrink: 0;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 69px;
  height: 100%;
`;

export const ColorInfo = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  padding: 14px 16px;
  box-sizing: border-box;
`;

export const UpperInfo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

export const LowerInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
`;

export const PickRatio = styled.div`
  font: ${({ theme }) => theme.font.textKR.Regular14};
  color: ${({ theme }) => theme.color.gray['600']};
  transition: color 0.2s ease;

  & > span {
    font: ${({ theme }) => theme.font.textKR.Medium14};
    transition: color 0.2s ease;
  }

  .selected & {
    color: ${({ theme }) => theme.color.gray['700']};
  }

  .selected & > span {
    color: ${({ theme }) => theme.color.activeBlue};
  }
`;

export const ColorName = styled.div`
  font: ${({ theme }) => theme.font.headKR.Medium14};
  color: ${({ theme }) => theme.color.gray['500']};
  transition: color 0.2s ease;

  .selected & {
    color: ${({ theme }) => theme.color.gray['900']};
  }
`;

export const Price = styled.div`
  font: ${({ theme }) => theme.font.textKR.Medium14};
  color: ${({ theme }) => theme.color.gray['600']};
  transition: color 0.2s ease;

  .selected & {
    color: ${({ theme }) => theme.color.gray['900']};
  }
`;

export const CheckIcons = styled(CheckSvg)`
  width: 24px;
  height: 24px;
  fill: ${({ theme }) => theme.color.gray['200']};
  transition: fill 0.2s ease;

  .selected & {
    fill: ${({ theme }) => theme.color.activeBlue};
  }
`;
