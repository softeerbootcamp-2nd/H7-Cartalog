import styled from 'styled-components';

export const Pick = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Header = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

export const ColorSet = styled.div`
  overflow: hidden;
`;

export const Color = styled.div`
  display: flex;
  flex-direction: row;
  margin-top: 12px;
  gap: 16px;
  transform: ${({ $position }) => `translateX(-${$position}px)`};
  transition: transform 0.5s ease-in-out;
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
  top: 800px;
  margin-bottom: 16px;
`;
