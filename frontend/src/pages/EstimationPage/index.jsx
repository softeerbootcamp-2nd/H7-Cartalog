import { useRef } from 'react';
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
    documentTitle: '내 차 만들기_견적서',
  });

  return (
    <S.Estimation>
      <S.PDF ref={pdfRef}>
        <Preview />
        <S.Info>
          <Info />
          <S.PageContents>
            <Detail />
            <HMGArea />
          </S.PageContents>
        </S.Info>
      </S.PDF>
      <PriceStaticBar
        min={SelectModel?.minPrice}
        max={SelectModel?.maxPrice}
        price={TotalPrice(data.price)}
      />
      <Footer pdfEvent={pdfEvent} />
    </S.Estimation>
  );
}

export default Estimation;
