import PropTypes from 'prop-types';
import * as S from './style';
import Title from '../../../components/Title';

const TYPE = 'dark';
const SUB_TITLE = '파워트레인';
const MAIN_TITLE = '컴포트 ll';

function Info({ imageUrl }) {
  const TitleProps = {
    type: TYPE,
    subTitle: SUB_TITLE,
    mainTitle: MAIN_TITLE,
  };

  return (
    <S.Info>
      <S.ModelText>
        <Title {...TitleProps} />
      </S.ModelText>

      <S.ModelImage src={imageUrl} />
    </S.Info>
  );
}

Info.propTypes = {
  imageUrl: PropTypes.string.isRequired,
};

export default Info;
