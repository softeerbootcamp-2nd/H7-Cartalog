import styled from 'styled-components';

export const Pick = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-top: 16px;
`;

export const OptionGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
`;

export const Footer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin-bottom: 16px;
`;

export const FooterEnd = styled.div`
  position: fixed;
  z-index: 2;
  bottom: 0;
  margin-bottom: 16px;
`;
