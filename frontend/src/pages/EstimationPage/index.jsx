import { useEffect, useState } from 'react';
import { useData, TotalPrice } from '../../utils/Context';
import PriceStaticBar from '../../components/PriceStaticBar';
import Preview from './Preview';
import * as S from './style';

function Estimation() {
  const [isFetched, setIsFetched] = useState(false);
  const { setTrimState, trim, price } = useData();
  const SelectModel = trim.fetchData.find((model) => model.id === trim.Id);

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
        <PriceStaticBar
          min={SelectModel?.minPrice}
          max={SelectModel?.maxPrice}
          price={TotalPrice(price)}
        />
      </S.EstimateFinish>
    </>
  ) : (
    <>Loading...</>
  );
}

export default Estimation;
