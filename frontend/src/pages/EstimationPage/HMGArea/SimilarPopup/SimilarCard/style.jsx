import { styled } from 'styled-components';
import { CardCss } from '../../../../../styles/GlobalStyle';

export const SimilarCard = styled.button`
  ${CardCss}
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  width: 103px;
  height: 107px;

  &:hover {
    border-color: ${({ theme }) => theme.color.activeBlue};
  }

  &.selected {
    border-color: ${({ theme }) => theme.color.activeBlue};
    background-color: ${({ theme }) => theme.color.cardBG};
  }
`;

export const SimilarImage = styled.img`
  width: 101px;
  height: 49px;
  object-fit: cover;
`;

export const SimilarInfo = styled.div`
  display: flex;
  flex-direction: column;
  height: 50px;
  padding: 8px;
`;

export const UpperInfo = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font: ${({ theme }) => theme.font.headKR.Regular12};
  color: ${({ theme }) => theme.color.gray['900']};
`;

export const LowerInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  width: 88px;

  .price {
    font: ${({ theme }) => theme.font.headKR.Regular10};
    color: ${({ theme }) => theme.color.gray['900']};
  }
`;
