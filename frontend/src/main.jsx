import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import { StateProvider } from './utils/Context';
import GlobalStyle from './styles/GlobalStyle';
import theme from './styles/themes';
import App from './App';

ReactDOM.createRoot(document.getElementById('root')).render(
  <ThemeProvider theme={theme}>
    <BrowserRouter>
      <GlobalStyle />
      <StateProvider>
        <App />
      </StateProvider>
    </BrowserRouter>
  </ThemeProvider>,
);
