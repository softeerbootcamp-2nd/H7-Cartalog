import { useData, TotalPrice } from '../../utils/Context';
import PriceStaticBar from '../PriceStaticBar';
import * as S from './style';

/**
 * Section 구역을 분리하는 컴포넌트
 * @param type {string} TrimSelect || ModelType || ExteriorColor || InteriorColor || AddOption
 * @param url {string} InteriorColor, AddOption에 필요한 이미지 URL
 * @param Info {Comment} 'Info' 구역에 안에 넣을 컴포넌트
 * @param Pick {Comment} 'Pick' 구역에 안에 넣을 컴포넌트
 * @returns
 */
function Section({ type, url, Info, Pick, showPriceStatic = true }) {
  const { trim, price } = useData();
  const SectionProps = { type, $url: url };
  const SelectModel = trim.fetchData.find((model) => model.id === trim.id);
  return (
    <S.Section>
      <S.Background {...SectionProps}>
        <S.Contents>{Info}</S.Contents>
      </S.Background>
      <S.Contents>{Pick}</S.Contents>
      {showPriceStatic && (
        <PriceStaticBar
          min={SelectModel?.minPrice}
          max={SelectModel?.maxPrice}
          price={TotalPrice(price)}
        />
      )}
    </S.Section>
  );
}

export default Section;
