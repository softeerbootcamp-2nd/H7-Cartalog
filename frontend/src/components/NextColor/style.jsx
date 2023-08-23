import styled from 'styled-components';
import { ReactComponent as ArrowLeft } from '../../../assets/icons/arrow_left.svg';
import { ReactComponent as ArrowRight } from '../../../assets/icons/arrow_right.svg';

export const NextColor = styled.div`
  display: flex;
  align-items: center;
  margin-top: 16px;
`;

export const ArrowLeftButton = styled(ArrowLeft)`
  fill: ${({ count, theme }) => (count === 1 ? theme.color.gray['200'] : theme.color.gray['600'])};
  cursor: pointer;
  padding: 8px;
`;

export const ArrowRightButton = styled(ArrowRight)`
  fill: ${({ count, page, theme }) =>
    count === page ? theme.color.gray['200'] : theme.color.gray['600']};
  cursor: pointer;
  padding: 8px;
`;

export const Page = styled.div`
  display: flex;
  margin-top: 3px;
  padding: 0 8px;
  color: ${({ theme }) => theme.color.gray['500']};
`;
