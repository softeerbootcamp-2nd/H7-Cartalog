import { styled } from 'styled-components';
import { ReactComponent as CheckSvg } from '../../../assets/icons/check.svg';

const CheckIcon = styled(CheckSvg)`
  width: 24px;
  height: 24px;
  fill: ${({ theme }) => theme.color.gray['200']};
  transition: fill 0.2s ease;

  .selected & {
    fill: ${({ theme }) => theme.color.activeBlue};
  }
`;

export default CheckIcon;
