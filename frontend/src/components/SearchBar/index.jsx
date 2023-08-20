import * as S from './style';

function SearchBar({ placeholder = '', value, onChange, onSubmit }) {
  const handleKeyDown = (event) => {
    if (event.key !== 'Enter') return;
    onSubmit();
  };

  return (
    <S.SearchBar>
      <S.SearchInput
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        onKeyDown={handleKeyDown}
        onSubmit={onSubmit}
      />
      <S.SearchButton type="button" onClick={onSubmit} />
    </S.SearchBar>
  );
}

export default SearchBar;
