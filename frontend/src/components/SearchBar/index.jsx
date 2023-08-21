import { useState } from 'react';
import * as S from './style';

function SearchBar({ placeholder = '', search }) {
  const [value, setValue] = useState('');
  const handleChange = (event) => {
    setValue(event.target.value);
  };
  const handleSubmit = () => {
    search(value);
  };
  const handleKeyDown = (event) => {
    if (event.key !== 'Enter') return;
    handleSubmit();
  };

  return (
    <S.SearchBar>
      <S.SearchInput
        placeholder={placeholder}
        value={value}
        onChange={handleChange}
        onKeyDown={handleKeyDown}
        onSubmit={handleSubmit}
      />
      <S.SearchButton type="button" onClick={handleSubmit} />
    </S.SearchBar>
  );
}

export default SearchBar;
