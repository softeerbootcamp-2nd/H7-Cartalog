import { useEffect, useState } from 'react';
import PriceStaticBar from '../../components/PriceStaticBar';
import Preview from './Preview';
import * as S from './style';

function EstimateFinish() {
  const [collapsed, setCollapsed] = useState(false);

  const handleScroll = () => {
    if (window.scrollY > 500) {
      setCollapsed(true);
    } else {
      setCollapsed(false);
    }
  };

  useEffect(() => {
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  return (
    <>
      <Preview collapsed={collapsed} />
      <S.EstimateFinish onScroll={handleScroll}>
        <div style={{ height: '3000px', width: '30px', backgroundColor: 'pink' }}>a</div>
        <PriceStaticBar min={10000000} max={50000000} price={25000000} />
      </S.EstimateFinish>
    </>
  );
}

export default EstimateFinish;
