import styled from 'styled-components';

export const HMGChart = styled.div`
  display: flex;
  flex-direction: column;
`;

export const Title = styled.h4`
  color: ${({ theme }) => theme.color.gray['600']};
  font: ${({ theme }) => theme.font.textKR.Medium10};
`;

export const Output = styled.h4`
  margin-top: 8px;
  color: ${({ theme }) => theme.color.gray['900']};
  font: ${({ theme }) => theme.font.headKR.Regular28};
`;

export const Chart = styled.div`
  position: relative;
  width: 200px;
  height: 4px;
  margin-top: 4px;
  background: ${({ theme }) => theme.color.gray['200']};
`;

export const Bar = styled.div`
  position: absolute;
  width: ${({ width }) => width};
  height: 4px;
  background-color: ${({ theme }) => theme.color.activeBlue2};
`;
