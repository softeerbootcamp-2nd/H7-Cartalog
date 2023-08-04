import styled from 'styled-components';

export const Home = styled.div`
  min-width: 100%;
`;

export const Shadow = styled.div`
  background: ${({ theme }) => theme.color.trimGrad};
  box-shadow: 0px 0px 8px 0px rgba(131, 133, 136, 0.2);
`;

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
  justify-content: space-between;
  width: 1280px;

  margin: 0 auto;
  padding: 0 128px;
  box-sizing: border-box;
`;
