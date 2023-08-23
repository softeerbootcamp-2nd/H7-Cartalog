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
import useFetch from '../../hooks/useFetch';

function Estimation() {
  const data = useData();
  const SelectModel = data.trim.fetchData.find((model) => model.id === data.trim.id);
  const pdfRef = useRef();
  const pdfEvent = useReactToPrint({
    content: () => pdfRef.current,
    documentTitle: '내 차 만들기_견적서',
  });
  const fetchedPostData = useFetch('estimates', {
    method: 'POST',
    body: {
      detailTrimId: data.modelType.detailTrimId,
      exteriorColorCode: data.exteriorColor.code,
      interiorColorCode: data.interiorColor.code,
      selectOptionOrPackageIds: data.optionPicker.chosenOptions,
    },
  });
  const fetchedGetData = useFetch(`estimates/distribution?trimId=${data.trim.id}`);

  useEffect(() => {
    if (!fetchedPostData || !fetchedGetData || data.estimation.isFetch || data.page !== 6) return;
    data.setTrimState((prevState) => ({
      ...prevState,
      page: 6,
      estimation: {
        ...prevState.estimation,
        isPost: true,
        id: fetchedPostData,
        isFetch: true,
        averagePrice: fetchedGetData,
      },
    }));
  }, [data, fetchedGetData, fetchedPostData]);

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
