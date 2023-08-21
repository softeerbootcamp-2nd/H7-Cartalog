import { styled } from 'styled-components';

export const DetailItem = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
  width: 100%;
  height: 55px;
  padding: 10px 0;
  border-bottom: 1px solid ${({ theme }) => theme.color.gray['50']};
`;

export const DetailImage = styled.img`
  min-width: 77px;
  max-width: 77px;
  height: 55px;
  object-fit: cover;
`;

export const Description = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

export const Area = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  gap: 8px;
  font: ${({ theme }) => theme.font.textKR.Regular14};
  color: ${({ theme }) => theme.color.gray['900']};

  & .title {
    color: ${({ theme }) => theme.color.gray['500']};
  }

  &:last-child {
    align-items: flex-end;
  }
`;
