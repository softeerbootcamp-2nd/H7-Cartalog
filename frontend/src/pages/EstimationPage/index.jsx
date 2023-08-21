import { useEffect, useRef } from 'react';
import { useReactToPrint } from 'react-to-print';
import { useData, TotalPrice } from '../../utils/Context';
import * as S from './style';
import PriceStaticBar from '../../components/PriceStaticBar';
import Preview from './Preview';
import Info from './Info';
import Detail from './Detail';
import HMGArea from './HMGArea';
import Footer from './Footer';

function Estimation() {
  const data = useData();
  const SelectModel = data.trim.fetchData.find((model) => model.id === data.trim.id);
  const pdfRef = useRef();
  const pdfEvent = useReactToPrint({
    content: () => pdfRef.current,
    documentTitle: '테스트',
  });

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
      <div ref={pdfRef}>
        <Preview />
        <S.Estimation>
          <Info />
          <S.PageContents>
            <Detail />
            <HMGArea />
          </S.PageContents>
        </S.Estimation>
      </div>

      <PriceStaticBar
        min={SelectModel?.minPrice}
        max={SelectModel?.maxPrice}
        price={TotalPrice(data.price)}
      />
      <Footer pdfEvent={pdfEvent} />
    </S.Estimation>
  ) : (
    <>Loading...</>
  );
}

export default Estimation;
