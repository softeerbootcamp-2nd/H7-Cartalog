import { useState } from 'react';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import TrimCard from './TrimCard';

const TRIM_DATA = [
  {
    name: 'Exclusive',
    description: '기본에 충실한 팰리세이드',
    price: '4,044,000',
  },
  {
    name: 'Le Blanc',
    description: '기본에 충실한 팰리세이드',
    price: '4,044,000',
  },
  {
    name: 'Prestige',
    description: '기본에 충실한 팰리세이드',
    price: '4,044,000',
  },
  {
    name: 'Calligraphy',
    description: '기본에 충실한 팰리세이드',
    price: '4,044,000',
  },
];

const PICK_MAIN_TITLE = '트림을 선택해주세요.';

function Pick() {
  const [active, setActive] = useState(null);
  const pickTitleProps = {
    mainTitle: PICK_MAIN_TITLE,
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.Trim>
        {TRIM_DATA.map((trim, index) => {
          const handleClick = () => setActive(index);

          return (
            <TrimCard
              key={trim.name}
              name={trim.name}
              description={trim.description}
              price={trim.price}
              active={active === index}
              onClick={handleClick}
            />
          );
        })}
      </S.Trim>
    </S.Pick>
  );
}

export default Pick;
