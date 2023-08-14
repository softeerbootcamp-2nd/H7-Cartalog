import { styled } from 'styled-components';

export const DetailItem = styled.div`
  display: flex;
  gap: 16px;
  width: 100%;
  height: 55px;
  padding: 8px 0;
  border-bottom: 1px solid ${({ theme }) => theme.color.gray['50']};

  & > img {
    width: 77px;
    height: 55px;
    object-fit: cover;
  }
`;

export const Description = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

export const Area = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  font: ${({ theme }) => theme.font.textKR.Regular14};
  color: ${({ theme }) => theme.color.gray['900']};

  & .title {
    color: ${({ theme }) => theme.color.gray['500']};
  }

  & button {
    font: ${({ theme }) => theme.font.headKR.Medium14};
    color: ${({ theme }) => theme.color.primary.default};
  }

  &:last-child {
    align-items: flex-end;
  }
`;
