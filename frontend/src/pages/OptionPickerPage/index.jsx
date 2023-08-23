import { useEffect, useState } from 'react';
import { useData } from '../../utils/Context';
import { OPTION_PICKER } from './constants';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';
import useFetch from '../../hooks/useFetch';

function OptionPicker() {
  const { setTrimState, page, modelType, interiorColor, optionPicker } = useData();
  const [selectedId, setSelectedId] = useState(null);
  const fetchedData = useFetch(
    `models/trims/options?detailTrimId=${modelType.detailTrimId}&interiorColorCode=${interiorColor.code}`,
  );

  useEffect(() => {
    if (!fetchedData || optionPicker.isFetch || page !== 5) return;
    setTrimState((prevState) => ({
      ...prevState,
      optionPicker: {
        ...prevState.optionPicker,
        isFetch: true,
        defaultOptions: [...fetchedData.defaultOptions],
        selectOptions: [...fetchedData.selectOptions],
        category: [...fetchedData.multipleSelectParentCategory],
      },
    }));
    setSelectedId(fetchedData.selectOptions[0].id);
  }, [
    fetchedData,
    interiorColor.code,
    modelType.detailTrimId,
    optionPicker.isFetch,
    page,
    setTrimState,
  ]);

  const SectionProps = {
    type: OPTION_PICKER.TYPE,
    Info: <Info optionId={selectedId} />,
    Pick: <Pick selected={selectedId} setSelected={setSelectedId} />,
  };

  return <Section {...SectionProps} />;
}

export default OptionPicker;
