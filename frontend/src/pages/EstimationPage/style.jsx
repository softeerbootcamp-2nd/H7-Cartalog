import { styled } from 'styled-components';
import { SlideFromBottom } from '../../styles/GlobalStyle';

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

export const PDF = styled.div`
  @media print {
    margin-top: -60px;
  }
`;

export const Info = styled.div`
  ${SlideFromBottom}
  animation-delay: 2.5s;
`;

export const PageContents = styled.div`
  display: flex;
  width: 1024px;
  margin: 0 auto;
  gap: 70px;
  margin-bottom: 200px;

  @media print {
    padding: 0 69px;
  }
`;
