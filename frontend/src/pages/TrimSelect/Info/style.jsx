import styled from 'styled-components';

export const Info = styled.div`
  display: flex;
`;

export const TrimText = styled.div`
  display: flex;
  flex-direction: column;
`;

export const HMG = styled.div`
  margin-top: 32px;
`;

export const HMGInfo = styled.div`
  display: flex;
  margin-top: 12px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.textKR.Medium12};
`;

export const Highlight = styled.div`
  color: ${({ theme }) => theme.color.activeBlue};
`;
