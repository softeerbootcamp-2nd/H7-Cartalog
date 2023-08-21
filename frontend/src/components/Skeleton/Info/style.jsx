import styled from 'styled-components';

export const Info = styled.div`
  display: flex;
  flex-direction: column;

  @keyframes loading {
    0% {
      transform: translateX(0);
    }
    100% {
      transform: translateX(1280px);
    }
  }

  ::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100px;
    height: 100%;
    background: linear-gradient(to right, #f2f2f2, #ddd, #f2f2f2);
    animation: loading 3s infinite linear;
  }
`;

export const SubTitle = styled.div`
  width: 180px;
  height: 22px;
  margin-top: 72px;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.gray['100']};

  overflow: hidden;
  position: relative;
`;

export const MainTitle = styled.div`
  width: 234px;
  height: 36px;
  margin-top: 5px;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.gray['100']};

  overflow: hidden;
  position: relative;
`;

export const MainInfo = styled.div`
  width: 448px;
  height: 100px;
  margin-top: 32px;
  border-radius: 5px;
  background-color: ${({ theme }) => theme.color.gray['100']};

  overflow: hidden;
  position: relative;
`;
