import { useEffect, useState, cloneElement } from 'react';
import { useData } from '../../utils/Context';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'OptionPicker';
const IMAGE_URL = '';

const MOCK_DATA = {
  multipleSelectParentCategory: ['상세 품목', '악세사리'],
  selectOptions: [
    {
      id: 11,
      name: '2열 통풍시트',
      parentCategory: '상세품목',
      childCategory: '시트',
      imageUrl:
        'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/le-blanc/options/alconbreak_s.jpg',
      price: 350000,
      chosen: 38,
      hashTags: ['장거리 운전', '쾌적'],
    },
    {
      id: 12,
      name: '운전석 전동시트',
      parentCategory: '상세품목',
      childCategory: '시트',
      imageUrl:
        'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/le-blanc/options/10_driverseat_s.jpg',
      price: 350000,
      chosen: 38,
      hashTags: ['장거리 운전', '쾌적'],
    },
    {
      id: 13,
      name: '좋아보이는 브레이크',
      parentCategory: '상세품목',
      childCategory: '휠',
      imageUrl:
        'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/le-blanc/options/alconbreak_s.jpg',
      price: 350000,
      chosen: 38,
      hashTags: ['장거리 운전', '쾌적'],
    },
  ],
  defaultOptions: [
    {
      id: 5,
      name: '디젤 2.2 엔진',
      parentCategory: null,
      childCategory: '파워트레인/성능',
      imageUrl:
        'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/le-blanc/options/dieselengine2.2_s.jpg',
      price: 0,
      chosen: 38,
      hashTags: ['장거리 운전'],
    },
  ],
};

function OptionPicker() {
  const { setTrimState, modelType, interiorColor, optionPicker } = useData();
  const [selectedId, setSelectedId] = useState(11);

  useEffect(() => {
    async function fetchData() {
      if (!optionPicker.isFetch) {
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
          clonePage: {
            ...prevState.clonePage,
            5: cloneElement(<OptionPicker />),
          },
        }));
      }
      setTrimState((prevState) => ({ ...prevState, page: 5 }));
      setTimeout(() => {
        setTrimState((prevState) => ({
          ...prevState,
          movePage: {
            ...prevState.movePage,
            clonePage: 5,
            nowContentRef: 'nowUnload',
            nextContentRef: 'nextUnload',
          },
        }));
      }, 1000);
    }
    fetchData();
  }, []);

  const InfoProps = { imageUrl: IMAGE_URL };
  const SectionProps = {
    type: TYPE,
    Info: <Info {...InfoProps} data={MOCK_DATA} selected={selectedId} />,
    Pick: <Pick data={MOCK_DATA} selected={selectedId} setSelected={setSelectedId} />,
  };

  return <Section {...SectionProps} />;
}

export default OptionPicker;
