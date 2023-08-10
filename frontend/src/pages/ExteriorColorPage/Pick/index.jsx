import { useState } from 'react';
import { useData, TotalPrice } from '../../../utils/Context';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import ColorCard from '../../../components/ColorCard';
import ColorChip from '../../../components/ColorChip';
import NextButton from '../../../components/NextButton';

const TYPE = 'buttonD';
const STATE = 'active';
const MAIN_TITLE = '다음';
const PICK_MAIN_TITLE = '외장 색상을 선택해주세요.';

const COLOR_DATA = [
  {
    id: 'A2B',
    name: '어비스 블랙 펄',
    price: 100000,
    chosen: 38,
  },
  {
    id: 'R2T',
    name: '쉬머링 실버 메탈릭',
    price: 100000,
    chosen: 38,
  },
  {
    id: 'R8N',
    name: '로버스트 에메랄드 펄',
    price: 100000,
    chosen: 38,
  },
  {
    id: 'UB7',
    name: '문라이트 블루 펄',
    price: 100000,
    chosen: 38,
  },
];

function Pick({ nextPage }) {
  const { modelType, price } = useData();
  console.log(modelType, TotalPrice(price));

  const pickTitleProps = { mainTitle: PICK_MAIN_TITLE };
  const [active, setActive] = useState(null);

  const nextButtonProps = {
    totalPrice: TotalPrice(price),
    estimateEvent: '',
    nextEvent: '',
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      {/* 컬러 칩들을 맵으로 생성 */}
      <S.Color>
        {COLOR_DATA.map((color) => (
          <ColorCard
            key={color.id}
            pickRatio={color.chosen}
            name={color.name}
            price={color.price}
            selected={active === color.id}
            onClick={() => setActive(color.id)}
          >
            <ColorChip color="#343736" />
          </ColorCard>
        ))}
      </S.Color>

      <S.Footer>
        <S.FooterEnd>
          <NextButton {...nextButtonProps} />
        </S.FooterEnd>
      </S.Footer>
    </S.Pick>
  );
}

export default Pick;
