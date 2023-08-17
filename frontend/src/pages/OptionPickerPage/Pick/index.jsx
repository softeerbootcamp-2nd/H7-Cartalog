import { useState } from 'react';
import * as S from './style';
import TypeSelector from './TypeSelector';
import CategorySelector from './CategorySelector';
import OptionCard from '../OptionCard';
import { useData } from '../../../utils/Context';

function Pick({ selected, setSelected }) {
  const { setTrimState, price, optionPicker } = useData();
  const [showDefault, setShowDefault] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const options = showDefault ? optionPicker.defaultOptions : optionPicker.selectOptions;
  const defaultCategories = [
    ...new Set(optionPicker.defaultOptions.map((option) => option.childCategory)),
  ];
  const categories = showDefault ? defaultCategories : optionPicker.category;
  const optionsToShow = options.filter((option) => {
    if (selectedCategory === null) return true;
    return option.childCategory === selectedCategory || option.parentCategory === selectedCategory;
  });

  const handleShowDefault = (value) => {
    if (showDefault === value) return;
    setShowDefault(value);
    setSelectedCategory(null);
  };

  function handleSelect(id) {
    setSelected(id);
  }
  function handleCheck(option) {
    const chosen = optionPicker.chosenOptions.includes(option.id);
    const updatedChosenOptions = chosen
      ? optionPicker.chosenOptions.filter((chosenOption) => chosenOption !== option.id)
      : [...optionPicker.chosenOptions, option.id];
    const updatedOptionPicker = {
      ...optionPicker,
      chosenOptions: updatedChosenOptions,
    };

    const updatedPrice = {
      ...price,
      optionPrice: price.optionPrice + (chosen ? -option.price : option.price),
    };

    setTrimState((prevState) => ({
      ...prevState,
      optionPicker: updatedOptionPicker,
      price: updatedPrice,
    }));
  }

  return (
    <S.Pick>
      <TypeSelector showDefault={showDefault} setShowDefault={handleShowDefault} />
      <CategorySelector
        data={categories}
        selectedCategory={selectedCategory}
        setSelectedCategory={setSelectedCategory}
      />
      <S.OptionGrid>
        {optionsToShow.map((option) => (
          <OptionCard
            key={option.id}
            name={option.name}
            pickRatio={option.chosen}
            hashtags={option.hashTags}
            price={option.price}
            imgSrc={option.imageUrl}
            hasHmgData={option.hasHMGData}
            selected={selected === option.id}
            checked={optionPicker.chosenOptions.includes(option.id)}
            select={() => handleSelect(option.id)}
            check={() => handleCheck(option)}
            isDefault={showDefault}
          />
        ))}
      </S.OptionGrid>
    </S.Pick>
  );
}

export default Pick;
