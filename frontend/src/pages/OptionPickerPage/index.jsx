import { useEffect, useState } from 'react';
import { useData } from '../../utils/Context';
import { OPTION_PICKER } from './constants';
import Skeleton from '../../components/Skeleton';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

function OptionPicker() {
  const { setTrimState, page, modelType, interiorColor, optionPicker } = useData();
  const [selectedId, setSelectedId] = useState(null);

  useEffect(() => {
    async function fetchData() {
      if (optionPicker.isFetch || page !== 5) return;
      const response = await fetch(
        `http://3.36.126.30/models/trims/options?detailTrimId=${modelType.detailTrimId}&interiorColorCode=${interiorColor.code}`,
      );
      const dataFetch = await response.json();

      setTrimState((prevState) => ({
        ...prevState,
        optionPicker: {
          ...prevState.optionPicker,
          isFetch: true,
          defaultOptions: [...dataFetch.defaultOptions],
          selectOptions: [...dataFetch.selectOptions],
          category: [...dataFetch.multipleSelectParentCategory],
        },
      }));
      setSelectedId(dataFetch.selectOptions[0].id);
    }
    fetchData();
  }, [interiorColor.code, modelType.detailTrimId, optionPicker.isFetch, page, setTrimState]);

  const SectionProps = {
    type: OPTION_PICKER.TYPE,
    Info: <Info optionId={selectedId} />,
    Pick: <Pick selected={selectedId} setSelected={setSelectedId} />,
  };

  const SkeletonProps = {
    type: OPTION_PICKER.TYPE,
  };

  return optionPicker.isFetch ? <Section {...SectionProps} /> : <Skeleton {...SkeletonProps} />;
}

export default OptionPicker;
