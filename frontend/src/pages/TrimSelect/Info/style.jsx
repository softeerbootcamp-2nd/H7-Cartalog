import styled from 'styled-components';

export const Info = styled.div`
  display: flex;
`;

export const Text = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Data = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 32px;
`;

// export const Info = styled.div`
//   display: flex;
//   align-items: center;
//   margin-top: 12px;
//   color: ${({ theme }) => theme.color.gray['900']};
//   font: ${({ theme }) => theme.font.textKR.Medium12};
// `;

export const MainInfo = styled.div`
  color: ${({ theme }) => theme.color.activeBlue};
`;
