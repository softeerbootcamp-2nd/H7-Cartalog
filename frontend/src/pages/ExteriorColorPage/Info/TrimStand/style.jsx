import { styled } from 'styled-components';

export const TrimStand = styled.div`
  position: absolute;
  width: 611px;
  height: 102px;
  margin-top: 228px;
  margin-left: 210px;
  border-radius: 50%;

  border: 2px solid transparent;
  background:
    linear-gradient(#f4f5f7, #f4f5f7) padding-box,
    linear-gradient(to top, #6d7786, transparent 90%) border-box;
`;

export const Stand = styled.div`
  position: absolute;
  display: flex;
  padding: 14px;
  bottom: 0;
  left: 50%;
  transform: translate(-50%, 50%);
  background-color: ${({ theme }) => theme.color.blueBG};
`;
