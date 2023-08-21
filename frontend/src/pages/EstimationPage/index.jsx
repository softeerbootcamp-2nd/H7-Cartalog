import { useEffect } from 'react';
import { useData, TotalPrice } from '../../utils/Context';
import * as S from './style';
import PriceStaticBar from '../../components/PriceStaticBar';
import Preview from './Preview';
import Info from './Info';
import Detail from './Detail';
import HMGArea from './HMGArea';
import Footer from './Footer';

const MOCK_HMGDATA = {
  trim: '르블랑',
  price: {
    min: 43460000,
    max: 47650000,
    current: 45800000,
  },
  value: 2500,
  similar: [
    {
      id: 1,
      value: 3422,
    },
    {
      id: 2,
      value: 3200,
    },
    {
      id: 3,
      value: 3000,
    },
    {
      id: 4,
      value: 2800,
    },
  ],
};

function Estimation() {
  const data = useData();
  const SelectModel = data.trim.fetchData.find((model) => model.id === data.trim.id);

  useEffect(() => {
    async function fetchData() {
      if (!data.estimation.isFetch && data.page === 6) {
        data.setTrimState((prevState) => ({
          ...prevState,
          estimation: {
            ...prevState.estimation,
            isFetch: true,
          },
        }));
      }
    }

    fetchData();
  }, [data]);

  return data.estimation.isFetch ? (
    <S.Estimation>
      <Preview />
      <S.Estimation>
        <Info />
        <S.PageContents>
          <Detail />
          <HMGArea data={MOCK_HMGDATA} />
        </S.PageContents>
        <Footer />
      </S.Estimation>

      <PriceStaticBar
        min={SelectModel?.minPrice}
        max={SelectModel?.maxPrice}
        price={TotalPrice(data.price)}
      />
    </S.Estimation>
  ) : (
    <>Loading...</>
  );
}

export default Estimation;
