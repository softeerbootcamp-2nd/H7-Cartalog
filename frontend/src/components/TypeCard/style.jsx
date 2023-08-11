import { styled } from 'styled-components';
import { CardCss } from '../../styles/GlobalStyle';

export const TypeCard = styled.button`
  ${CardCss}
  display: flex;
  flex-direction: column;
  width: 160px;
  height: 76px;
  padding: 8px 12px;
  box-sizing: border-box;
  background-color: ${({ theme }) => theme.color.skyBlue4c};

  & .pickRatio {
    margin-bottom: -3px;
  }
`;

export const Info = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;
