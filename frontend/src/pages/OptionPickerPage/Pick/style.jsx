import styled from 'styled-components';

export const Pick = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-top: 16px;
  margin-bottom: 120px;
`;

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const OptionGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
`;
