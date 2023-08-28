import { useEffect } from 'react';
import { useData } from '../../../utils/Context';
import * as S from './style';
import PriceCard from './PriceCard';
import SimilarCard from './SimilarCard';
import { URL } from '../../../constants';

function HMGArea() {
  const data = useData();

  useEffect(() => {
    if (data.estimation.isFetch || data.page !== 6) return;
    const fetchData = async () => {
      const [estimateId, averagePrice] = await Promise.all([
        fetch(`${URL}estimates`, {
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
        }).then((res) => res.json()),
        fetch(`${URL}estimates/distribution?trimId=${data.trim.id}`).then((res) => res.json()),
      ]);
      const similarEstimateCountInfo = await fetch(
        `${URL}similarity/countInfo?estimateId=${estimateId}`,
      ).then((res) => res.json());

      data.setTrimState((prevState) => ({
        ...prevState,
        estimation: {
          ...prevState.estimation,
          isPost: true,
          id: estimateId,
          isFetch: true,
          averagePrice,
          similarEstimateCountInfo,
        },
      }));
    };

    fetchData();
  }, [data]);

  if (!data.estimation.isFetch) return <S.HMGArea />;

  return (
    <S.HMGArea>
      <PriceCard name={data.trim.name} averagePrice={data.estimation.averagePrice} />
      {data.estimation.similarEstimateCountInfo.similarEstimateCounts.length !== 0 && (
        <SimilarCard data={data} />
      )}
    </S.HMGArea>
  );
}

export default HMGArea;
