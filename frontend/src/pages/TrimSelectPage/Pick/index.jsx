import { useState } from 'react';
import * as S from './style';
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

function Pick({ nextPage }) {
  const [active, setActive] = useState(null);

  return (
    <S.Pick>
      <S.Title>트림을 선택해주세요</S.Title>
      <S.Trim>
        {TRIM_DATA.map((trim, index) => {
          const handleClick = () => setActive(index);

          return (
            <TrimCard
              key={index}
              name={trim.name}
              description={trim.description}
              price={trim.price}
              active={active === index}
              onClick={handleClick}
              nextPage={nextPage}
            />
          );
        })}
      </S.Trim>
    </S.Pick>
  );
}

export default Pick;
