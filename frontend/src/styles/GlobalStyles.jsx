import { createGlobalStyle } from 'styled-components';
import HyundaiSansHeadKRBold from '../../assets/fonts/HyundaiSansHeadKROTFBold.woff2';
import HyundaiSansHeadKRMedium from '../../assets/fonts/HyundaiSansHeadKROTFMedium.woff2';
import HyundaiSansHeadKRRegular from '../../assets/fonts/HyundaiSansHeadKROTFRegular.woff2';

import HyundaiSansTextKRBold from '../../assets/fonts/HyundaiSansTextKROTFBold.woff2';
import HyundaiSansTextKRMedium from '../../assets/fonts/HyundaiSansTextKROTFMedium.woff2';
import HyundaiSansTextKRRegular from '../../assets/fonts/HyundaiSansTextKROTFRegular.woff2';

const GlobalStyle = createGlobalStyle`
  @font-face {
    font-family: 'HyundaiSansHeadKRBold';
    src: url(${HyundaiSansHeadKRBold}) format('woff2');
    font-weight: 700;
    font-style: normal;
  }
  @font-face {
    font-family: 'HyundaiSansHeadKRMedium';
    src: url(${HyundaiSansHeadKRMedium}) format('woff2');
    font-weight: 500;
    font-style: normal;
  }
  @font-face {
    font-family: 'HyundaiSansHeadKRRegular';
    src: url(${HyundaiSansHeadKRRegular}) format('woff2');
    font-weight: 400;
    font-style: normal;
  }

  @font-face {
    font-family: 'HyundaiSansTextKRBold';
    src: url(${HyundaiSansTextKRBold}) format('woff2');
    font-weight: 700;
    font-style: normal;
  }
  @font-face {
    font-family: 'HyundaiSansTextKRMedium';
    src: url(${HyundaiSansTextKRMedium}) format('woff2');
    font-weight: 500;
    font-style: normal;
  }
  @font-face {
    font-family: 'HyundaiSansTextKRRegular';
    src: url(${HyundaiSansTextKRRegular}) format('woff2');
    font-weight: 400;
    font-style: normal;
  }
  * {
    letter-spacing: -0.003em;
  }
  body {
    margin: 0;
    padding: 0;
  }
  a {
    text-decoration: none;
    color: inherit;
  }
  button {
    border: none;
    outline: none;
    background-color: transparent;
    cursor: pointer;
  }
  input {
    border: none;
    outline: none;
  }
  ul {
    list-style: none;
    padding: 0;
    margin: 0;
  }
`;

export default GlobalStyle;
