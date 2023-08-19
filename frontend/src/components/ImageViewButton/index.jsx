import * as S from './style';

function ImageViewButton({ onClick, text }) {
  return <S.ImageViewButton onClick={onClick}>{text}</S.ImageViewButton>;
}

export default ImageViewButton;
