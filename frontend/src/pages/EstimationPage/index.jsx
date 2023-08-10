import { useEffect, useState } from 'react';
import { useData } from '../../utils/Context';
import PriceStaticBar from '../../components/PriceStaticBar';
import Preview from './Preview';
import * as S from './style';

function Estimation() {
  const [isFetched, setIsFetched] = useState(false);
  const { setTrimState } = useData();

  useEffect(() => {
    async function fetchData() {
      // !FIX API 데이터 받아오도록 수정해야함
      setTrimState((prevState) => ({
        ...prevState,
        page: 6,
      }));
      setIsFetched(true);
    }
    fetchData();
  }, []);

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

  return isFetched ? (
    <>
      <Preview collapsed={collapsed} />
      <S.EstimateFinish onScroll={handleScroll}>
        <div style={{ height: '3000px', width: '30px', backgroundColor: 'pink' }}>a</div>
        <PriceStaticBar min={10000000} max={50000000} price={25000000} />
      </S.EstimateFinish>
    </>
  ) : (
    <>Loding...</>
  );
}

export default Estimation;