import { styled } from 'styled-components';

export const InfoPanel = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 324px;
  gap: 8px;
`;

export const LineInfo = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
`;

export const Wrapper = styled.div`
  display: flex;
  gap: 8px;
  align-items: center;
`;

export const Title = styled.div`
  width: 56px;
  font: ${({ theme }) => theme.font.textKR.Regular12};
  color: ${({ theme }) => theme.color.gray['500']};
`;

export const Content = styled.div`
  width: 160px;
  font: ${({ theme }) => theme.font.textKR.Medium12};
  color: ${({ theme }) => theme.color.gray['900']};
  word-break: keep-all;
`;

export const Price = styled.div`
  font: ${({ theme }) => theme.font.textKR.Regular14};
  color: ${({ theme }) => theme.color.gray['900']};
`;

export const Divider = styled.div`
  width: 100%;
  height: 1px;
  margin: 8px 0;
  background-color: ${({ theme }) => theme.color.blueBG};
`;
