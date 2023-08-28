import { styled } from 'styled-components';

// !FIX 이 린트 설정 없애는 거 어떤가요
// eslint-disable-next-line import/prefer-default-export
export const ColorChip = styled.div`
  ${({ type, theme }) => {
    if (type === 'ExteriorColor') {
      return `
      width: 58px;
      height: 58px;
      border-radius: 50%;
      box-sizing: border-box;
      border: 1px solid ${theme.color.gray['50']};
      padding: 6px;
      transition: border-color 0.2s ease;

      .selected & {
        border-color: ${theme.color.primary.default};
      }

      & > img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
        object-fit: cover;
      }
      `;
    }
    if (type === 'InteriorColor') {
      return `
      width: 100%;
      height: 100%;

      .selected & {
        border-color: ${theme.color.primary.default};
      }

      & > img {
        transform: rotate(90deg);
        width: 108px;
        height: 69px;
        margin-top: 19.5px;
        margin-left: -19.5px;
        border-radius: 1px;
        object-fit: cover;
      }
      `;
    }
  }}
`;
