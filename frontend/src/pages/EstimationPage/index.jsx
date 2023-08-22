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
    documentTitle: '내 차 만들기_견적서',
  });

  useEffect(() => {
    async function fetchData() {
      if (!data.estimation.isFetch && data.page === 6) {
        try {
          const response = await fetch('http://3.36.126.30/estimates', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              detailTrimId: data.modelType.detailTrimId,
              exteriorColorCode: data.exteriorColor.code,
              interiorColorCode: data.interiorColor.code,
              selectOptionOrPackageIds: data.optionPicker.chosenOptions,
            }),
          });

          if (response.ok) {
            const responseData = await response.json();

            data.setTrimState((prevState) => ({
              ...prevState,
              page: 6,
              estimation: {
                ...prevState.estimation,
                isPost: true,
                id: responseData,
              },
            }));
          } else {
            console.error('견적서 요청 실패', response.status);
          }
        } catch (error) {
          console.error('네트워크 오류', error);
        } finally {
          if (!data.estimation.isFetch) {
            const response = await fetch(
              `http://3.36.126.30/estimates/distribution?trimId=${data.trim.id}`,
            );
            const dataFetch = await response.json();

            data.setTrimState((prevState) => ({
              ...prevState,
              estimation: {
                ...prevState.estimation,
                isFetch: true,
                averagePrice: dataFetch,
              },
            }));
          }
        }
      }
    }

    fetchData();
  }, [data]);

  return data.estimation.isFetch ? (
    <S.Estimation>
      <S.PDF ref={pdfRef}>
        <Preview />
        <Info />
        <S.PageContents>
          <Detail />
          <HMGArea />
        </S.PageContents>
      </S.PDF>

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
