import { useState } from 'react';
import * as S from './style';
import TypeSelector from './TypeSelector';
import CategorySelector from './CategorySelector';
import OptionCard from '../OptionCard';
import { useData } from '../../../utils/Context';
import SearchBar from '../../../components/SearchBar';

const PLACEHOLDER = '옵션명, 해시태그, 카테고리로 검색해보세요.';

function Pick({ selected, setSelected }) {
  const { setTrimState, price, optionPicker } = useData();
  const [showDefault, setShowDefault] = useState(false);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [searchText, setSearchText] = useState('');
  const options = showDefault ? optionPicker.defaultOptions : optionPicker.selectOptions;
  const defaultCategories = [
    ...new Set(optionPicker.defaultOptions.map((option) => option.childCategory)),
  ];
  const categories = showDefault ? defaultCategories : optionPicker.category;
  const searched = (option) =>
    searchText === '' ||
    option.name.includes(searchText) ||
    option.childCategory?.includes(searchText) ||
    option.parentCategory?.includes(searchText) ||
    option.hashTags.some((hashTag) => hashTag.includes(searchText));

  const optionsToShow = options.filter((option) => {
    if (!searched(option)) return false;
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
    const updatedChosenOptionsData = updatedChosenOptions.map((chosenOption) =>
      optionPicker.selectOptions.find((selectOption) => selectOption.id === chosenOption),
    );

    const updatedOptionPicker = {
      ...optionPicker,
      chosenOptions: updatedChosenOptions,
      chosenOptionsData: updatedChosenOptionsData,
      isExpend: updatedChosenOptions.length !== 0,
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
      <S.Header>
        <TypeSelector showDefault={showDefault} setShowDefault={handleShowDefault} />
        <SearchBar
          placeholder={PLACEHOLDER}
          search={(text) => {
            setSearchText(text);
          }}
        />
      </S.Header>
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
