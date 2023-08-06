import styled, { StyleSheetManager } from 'styled-components';

export const Section = styled.div`
  position: relative;
  min-width: 100%;
  margin-top: 60px;
`;

const styleByType = {
  TrimSelect: ({ theme }) => `
    background: ${theme.color.trimGrad};
  `,
  ModelType: ({ theme }) => `
    display: flex;
    flex-direction: row;
    background: ${theme.color.modelGrad};
  `,
  InteriorColor: ({ theme }) => `
    position: relative;
    background: ${theme.color.modelGrad};
  `,
  AddOption: () => `
    display: flex;
    flex-direction: row;
  `,
};

export const Background = styled.div`
  ${({ type, theme }) => styleByType[type] && styleByType[type]({ type, theme })};
  height: 360px;
  box-shadow: 0px 0px 8px 0px rgba(131, 133, 136, 0.2);
`;

export const BackgroundImage = styled.img`
  position: absolute;
  width: 100%;
  height: 360px;
  object-fit: cover;
  object-position: 25% 25%;
`;

export const ColorDiv = styled.div`
  min-width: 50%;
  ${({ type, theme }) => type === 'AddOption' && `background: ${theme.color.blueBG}`};
`;

export const ImageDiv = styled.img`
  min-width: 50%;
  object-fit: cover;
`;

export const Contents = styled.div`
  ${({ type }) => type === 'InteriorColor' && 'position: absolute'};
  display: flex;
  flex-direction: column;
  width: 1280px;
  margin: 0 auto;
  padding: 0 128px;
  box-sizing: border-box;
`;
