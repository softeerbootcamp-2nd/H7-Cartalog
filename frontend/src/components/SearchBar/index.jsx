import { useState } from 'react';
import * as S from './style';

function SearchBar({ placeholder = '', search }) {
  const [value, setValue] = useState('');
  const handleChange = (event) => {
    setValue(event.target.value);
    search(event.target.value);
  };

  return (
    <S.SearchBar>
      <S.SearchInput placeholder={placeholder} value={value} onChange={handleChange} />
      <S.SearchButton type="button" />
    </S.SearchBar>
  );
}

export default SearchBar;
