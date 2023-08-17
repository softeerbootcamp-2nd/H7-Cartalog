import RoundButton from '../../../../components/RoundButton';
import * as S from './style';

function CategorySelector({ data, selectedCategory, setSelectedCategory }) {
  return (
    <S.CategorySelector>
      <RoundButton
        type="option"
        state={selectedCategory === null ? 'active' : 'inactive'}
        title="전체"
        clickEvent={() => setSelectedCategory(null)}
      />
      {data.map((category) => {
        const buttonState = selectedCategory === category ? 'active' : 'inactive';
        const handleClick = () => {
          if (selectedCategory === category) return;
          setSelectedCategory(category);
        };

        return (
          <RoundButton
            key={category}
            type="option"
            state={buttonState}
            title={category}
            clickEvent={handleClick}
          />
        );
      })}
    </S.CategorySelector>
  );
}

export default CategorySelector;
