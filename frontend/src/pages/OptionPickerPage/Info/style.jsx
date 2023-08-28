import styled from 'styled-components';

export const Info = styled.div`
  display: flex;

  & > button {
    position: absolute;
    bottom: 18px;
    left: 50%;
    transform: translateX(-50%);
  }
`;

export const ModelText = styled.div`
  display: flex;
  flex-direction: column;
  min-width: 520px;
`;

export const ModelImage = styled.img`
  width: 632px;
  height: 360px;
`;
