import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GlobalStyle from './styles/GlobalStyle';
import { StateProvider } from './utils/Context';
import theme from './styles/themes';
import App from './App';

ReactDOM.createRoot(document.getElementById('root')).render(
  // <React.StrictMode>
  <ThemeProvider theme={theme}>
    <BrowserRouter>
      <GlobalStyle />
      <StateProvider>
        <App />
      </StateProvider>
    </BrowserRouter>
  </ThemeProvider>,
  // </React.StrictMode>,
);
