import styled, { keyframes } from 'styled-components';

const backgroundChange = keyframes`
    0%,
    100% {
      background-color: #EDEDEEaa;
    }
    50% {
      background-color: #DADCDDaa;
    }
`;

export const Info = styled.div`
  display: flex;
  flex-direction: column;
`;

export const SubTitle = styled.div`
  width: 180px;
  height: 22px;
  margin-top: 72px;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.gray['100']};
  animation: ${backgroundChange} 2s infinite;
`;

export const MainTitle = styled.div`
  width: 234px;
  height: 36px;
  margin-top: 5px;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.gray['100']};
  animation: ${backgroundChange} 2s infinite;
`;

export const MainInfo = styled.div`
  width: 448px;
  height: 100px;
  margin-top: 75px;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.gray['100']};
  animation: ${backgroundChange} 2s infinite;
`;
