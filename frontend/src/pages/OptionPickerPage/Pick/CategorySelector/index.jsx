import RoundButton from '../../../../components/RoundButton';
import * as S from './style';

function CategorySelector({ data }) {
  return (
    <S.CategorySelector>
      <RoundButton type="option" state="active" title="전체" />
      {data.map((category) => (
        <RoundButton key={category} type="option" state="inactive" title={category} />
      ))}
    </S.CategorySelector>
  );
}

export default CategorySelector;
