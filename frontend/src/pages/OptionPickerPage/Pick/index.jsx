import { useState } from 'react';
import * as S from './style';
import TypeSelector from './TypeSelector';
import CategorySelector from './CategorySelector';
import OptionCard from '../OptionCard';
import NextButton from '../../../components/NextButton';

const MOCK_CATEGORY = ['상세품목', '액세서리', '휠'];

function initData(data) {
  const result = {};

  data.forEach((item) => {
    result[item] = false;
  });
  return result;
}

function Pick({ data, selected, setSelected }) {
  const [showDefault, setShowDefault] = useState(false);
  const options = showDefault ? data.defaultOptions : data.selectOptions;
  const [selectedOptions, setSelectedOptions] = useState(initData(options));

  function handleSelect(id) {
    setSelected(id);
  }
  function handleCheck(id) {
    setSelectedOptions((prev) => ({ ...prev, [id]: !prev[id] }));
  }

  return (
    <S.Pick>
      <TypeSelector showDefault={showDefault} setShowDefault={setShowDefault} />
      <CategorySelector data={MOCK_CATEGORY} />
      <S.OptionGrid>
        {options.map((option) => (
          <OptionCard
            key={option.id}
            name={option.name}
            pickRatio={option.chosen}
            hashtags={option.hashTags}
            price={option.price}
            imgSrc={option.imageUrl}
            hasHmgData
            selected={selected === option.id}
            checked={selectedOptions[option.id]}
            select={() => handleSelect(option.id)}
            check={() => handleCheck(option.id)}
            isDefault={showDefault}
          />
        ))}
      </S.OptionGrid>
      <S.Footer>
        <S.FooterEnd>
          <NextButton />
        </S.FooterEnd>
      </S.Footer>
    </S.Pick>
  );
}

export default Pick;
