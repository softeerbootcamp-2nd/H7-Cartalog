import { useEffect } from 'react';
import { useData } from '../../../utils/Context';
import * as S from './style';
import PriceCard from './PriceCard';
import SimilarCard from './SimilarCard';
import useFetch from '../../../hooks/useFetch';

function HMGArea() {
  const data = useData();
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
    <S.HMGArea>
      <PriceCard />
      {data.estimation.similarEstimateCounts.length !== 0 && <SimilarCard />}
      <SimilarCard />
    </S.HMGArea>
  );
}

export default HMGArea;
