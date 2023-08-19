import { styled } from 'styled-components';

export const Estimation = styled.div`
  min-width: max(1280px, 100%);
  min-height: max(720px, 100%);
  height: 100vh;
  box-sizing: border-box;
  overflow: scroll;

  ::-webkit-scrollbar {
    display: none;
  }
`;

export const PageContents = styled.div`
  display: flex;
  width: 1024px;
  margin: 0 auto;
  gap: 70px;
`;
