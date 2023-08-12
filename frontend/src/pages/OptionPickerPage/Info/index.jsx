import * as S from './style';
import Title from '../../../components/Title';

const TYPE = 'dark';

function mockInfo(id) {
  return {
    category: '파워트레인',
    name: `옵션이름${id}`,
    description:
      '시동이 걸린 상태에서 해당 좌석의 통풍 스위치를 누르면 표시등이 켜지면서 해당 좌석에 바람이 나오는 편의장치입니다.',
  };
}

function Info({ imageUrl, data, selected }) {
  const mockData = mockInfo(selected);
  const TitleProps = {
    type: TYPE,
    subTitle: mockData.category,
    mainTitle: mockData.name,
    description: mockData.description,
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

export default Info;
