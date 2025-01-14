import styled from 'styled-components';
import { EASE_OUT_CUBIC } from '../../constants';

export const Section = styled.div`
  position: relative;
  min-width: max(1280px, 100%);
  min-height: max(720px, 100%);
  height: 100vh;
  padding-top: 60px;
  box-sizing: border-box;
  overflow: scroll;
`;

const SectionByType = {
  TrimSelect: ({ theme }) => `
    background: ${theme.color.trimGrad};
  `,
  ModelType: ({ theme }) => `
    background: ${theme.color.modelGrad};
  `,
  ExteriorColor: ({ theme }) => `
    background: ${theme.color.blueBG};
  `,
  InteriorColor: ({ $url }) => `
    background-image: url(${$url});
    background-repeat : no-repeat;
    background-size : cover;
  `,
  OptionPicker: ({ theme }) => `
    background: ${theme.color.blueBG};
    position: sticky;
    top: -250px;
    z-index: 3;
    transition: top 0.25s ${EASE_OUT_CUBIC};

    .expanded & {
      top: 0;
    }
  `,
};

export const Background = styled.div`
  ${({ type, theme, $url }) => SectionByType[type] && SectionByType[type]({ type, theme, $url })};
  height: 360px;
  box-shadow: 0px 0px 8px 0px rgba(131, 133, 136, 0.2);
`;

export const Contents = styled.div`
  display: flex;
  flex-direction: column;
  width: 1280px;
  margin: 0 auto;
  padding: 0 128px;
  box-sizing: border-box;
`;
