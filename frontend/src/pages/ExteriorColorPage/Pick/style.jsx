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
