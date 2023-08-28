import { styled } from 'styled-components';

export const HMGInfo = styled.div`
  margin-bottom: 32px;
`;

export const HMGInfoData = styled.div`
  display: flex;
  margin-top: 12px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.textKR.Medium12};
  font-display: swap;
`;

export const Highlight = styled.div`
  color: ${({ theme }) => theme.color.activeBlue};
`;
