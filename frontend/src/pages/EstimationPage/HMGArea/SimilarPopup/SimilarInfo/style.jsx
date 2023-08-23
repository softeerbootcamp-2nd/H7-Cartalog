import { styled } from 'styled-components';

export const SimilarInfo = styled.div`
  display: flex;
  width: 762px;
  height: 224px;
  flex-shrink: 0;

  border-radius: 2px;
  border: 1px solid ${({ theme }) => theme.color.activeBlue};
`;

export const LeftArea = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  width: 486px;
  height: 224px;

  & > img {
    width: 274px;
    height: 156px;
    object-fit: cover;
    margin-right: 31px;
  }
`;

export const ArrowButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 8px;

  & > svg {
    fill: ${({ theme }) => theme.color.gray['200']};
  }

  &.active > svg {
    fill: ${({ theme }) => theme.color.gray['600']};
  }
`;
export const LeftInfo = styled.div`
  display: flex;
  flex-direction: column;
  width: 165px;
  margin-left: 8px;
`;

export const SubTitle = styled.div`
  font: ${({ theme }) => theme.font.headKR.Regular10};
  color: ${({ theme }) => theme.color.gray['900']};
`;

export const MainTitle = styled.div`
  font: ${({ theme }) => theme.font.headKR.Bold18};
  color: ${({ theme }) => theme.color.primary['700']};
`;

export const Price = styled.div`
  margin-top: 23px;
  font: ${({ theme }) => theme.font.headKR.Medium14};
  color: ${({ theme }) => theme.color.primary['700']};
`;

export const RightArea = styled.div`
  display: flex;
  flex-direction: row;
  width: 275px;
  height: 224px;
  flex-shrink: 0;

  background-color: ${({ theme }) => theme.color.gray['50']};
`;

export const RightInfo = styled.div`
  display: flex;
  flex-direction: column;
  width: 235px;
`;

export const TagWrapper = styled.div`
  margin-top: 29px;
`;

export const OptionWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 10px;
  margin-left: 16px;
`;

export const OptionTitle = styled.div`
  margin-bottom: 4px;
  font: ${({ theme }) => theme.font.headKR.Regular10};
  color: ${({ theme }) => theme.color.gray['900']};
`;

export const CardWrapper = styled.div`
  display: flex;
  width: 220px;
  gap: 8px;
`;
