import { createGlobalStyle, css, keyframes } from 'styled-components';

import HyundaiSansHeadBold from '../../assets/fonts/HyundaiSansHead-Bold.woff2';
import HyundaiSansHeadKRBold from '../../assets/fonts/HyundaiSansHeadKROTFBold.woff2';
import HyundaiSansHeadKRMedium from '../../assets/fonts/HyundaiSansHeadKROTFMedium.woff2';
import HyundaiSansHeadKRRegular from '../../assets/fonts/HyundaiSansHeadKROTFRegular.woff2';
import HyundaiSansTextMedium from '../../assets/fonts/HyundaiSansText-Medium.woff2';
import HyundaiSansTextKRBold from '../../assets/fonts/HyundaiSansTextKROTFBold.woff2';
import HyundaiSansTextKRMedium from '../../assets/fonts/HyundaiSansTextKROTFMedium.woff2';
import HyundaiSansTextKRRegular from '../../assets/fonts/HyundaiSansTextKROTFRegular.woff2';

const GlobalStyle = createGlobalStyle`
  @font-face {
    font-family: 'HyundaiSansHeadBold';
    src: url(${HyundaiSansHeadBold}) format('woff2');
    font-weight: 700;
    font-style: normal;
  }
  @font-face {
    font-family: 'HyundaiSansTextMedium';
    src: url(${HyundaiSansTextMedium}) format('woff2');
    font-weight: 500;
    font-style: normal;
  }
  
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
  p, h1, h2, h3, h4, h5, h6 {
    margin: 0;
  }
`;

const fadeInWithTransformAnimation = keyframes`
  0% {
    opacity: 0;
    transform: translate(-50%, -45%);
  }
  100% {
    opacity: 1;
    transform: translate(-50%, -50%);
  }
`;

const fadeInAnimation = keyframes`
  0% { opacity: 0; }
  100% { opacity: 1; }
`;

export const FadeInWithTransform = css`
  animation: ${fadeInWithTransformAnimation} 0.25s ease-out forwards;
`;

export const FadeIn = css`
  animation: ${fadeInAnimation} 0.25s ease-out forwards;
`;

export const CardCss = css`
  border-radius: 2px;
  border: 1px solid ${({ theme }) => theme.color.gray['200']};
  transition:
    border-color 0.2s ease,
    background-color 0.2s ease;

  &:hover {
    border-color: ${({ theme }) => theme.color.activeBlue};
  }

  &.selected {
    border-color: ${({ theme }) => theme.color.activeBlue};
    background-color: ${({ theme }) => theme.color.cardBG};

    & .pickRatio {
      color: ${({ theme }) => theme.color.gray['700']};

      & > span {
        color: ${({ theme }) => theme.color.activeBlue};
      }
    }

    & .title,
    & .price {
      color: ${({ theme }) => theme.color.gray['900']};
    }
  }

  & .pickRatio {
    font: ${({ theme }) => theme.font.textKR.Regular12};
    color: ${({ theme }) => theme.color.gray['600']};
    transition: color 0.2s ease;
  }

  & .price {
    font: ${({ theme }) => theme.font.textKR.Medium14};
    color: ${({ theme }) => theme.color.gray['600']};
    transition: color 0.2s ease;
  }

  & .title {
    font: ${({ theme }) => theme.font.headKR.Medium14};
    color: ${({ theme }) => theme.color.gray['500']};
    transition: color 0.2s ease;
  }
`;

export default GlobalStyle;
