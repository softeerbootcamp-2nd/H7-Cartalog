import styled from 'styled-components';

export const Header = styled.header`
  display: flex;
  justify-content: space-between;
  position: fixed;
  top: 0;
  z-index: 1;
  width: 100%;
  height: 60px;
  background-color: ${({ theme }) => theme.color.white};
  border-bottom: 2px solid ${({ theme }) => theme.color.gray['200']};
  font: ${({ theme }) => theme.font.textKR.Medium14};
`;

export const Contents = styled.div`
  display: flex;
  flex-shrink: 0;
  align-items: flex-end;
  justify-content: space-between;
  width: 1280px;
  margin: 0 auto;
  padding: 0 69px;
  box-sizing: border-box;
`;
