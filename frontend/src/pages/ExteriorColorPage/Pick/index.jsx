import { useState } from 'react';
import * as S from './style';
import Button from '../../../components/Button';
import PickTitle from '../../../components/PickTitle';
import ColorCard from '../../../components/ColorCard';
import ColorChip from '../../../components/ColorChip';

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
  const pickTitleProps = { mainTitle: PICK_MAIN_TITLE };
  const [active, setActive] = useState(null);

  const buttonProps = {
    nextPage,
    type: TYPE,
    state: STATE,
    mainTitle: MAIN_TITLE,
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

      <Button {...buttonProps} />
    </S.Pick>
  );
}

export default Pick;
