import { useState } from 'react';
import Section from '../../components/Section';
import Info from './Info';
import Pick from './Pick';

const TYPE = 'OptionPicker';
const IMAGE_URL = '../../../../../assets/images/TrimSelect/interior.png';

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
  const [selectedId, setSelectedId] = useState(11);

  const InfoProps = { imageUrl: IMAGE_URL };
  const SectionProps = {
    type: TYPE,
    Info: <Info {...InfoProps} data={MOCK_DATA} selected={selectedId} />,
    Pick: <Pick data={MOCK_DATA} selected={selectedId} setSelected={setSelectedId} />,
  };

  return <Section {...SectionProps} />;
}

export default OptionPicker;
