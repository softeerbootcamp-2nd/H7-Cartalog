import { styled } from 'styled-components';

// eslint-disable-next-line import/prefer-default-export
export const ColorChip = styled.div`
  width: 58px;
  height: 58px;
  border-radius: 50%;
  box-sizing: border-box;
  border: 1px solid ${({ theme }) => theme.color.gray['50']};
  padding: 6px;
  transition: border-color 0.2s ease;

  .selected & {
    border-color: ${({ theme }) => theme.color.primary.default};
  }

  & > img {
    width: 100%;
    height: 100%;
    border-radius: 50%;
    object-fit: cover;
  }
`;
