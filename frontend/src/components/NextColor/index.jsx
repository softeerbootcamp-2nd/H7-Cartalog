import { useData } from '../../utils/Context';
import * as S from './style';

function NextColor() {
  const { setTrimState, exteriorColor } = useData();
  const arrowProps = { count: exteriorColor.count, page: exteriorColor.page };

  return (
    <S.NextColor>
      <S.ArrowLeftButton
        {...arrowProps}
        onClick={() => {
          if (exteriorColor.count > 1) {
            setTrimState((prevState) => ({
              ...prevState,
              exteriorColor: {
                ...prevState.exteriorColor,
                position: exteriorColor.position - 260,
                count: exteriorColor.count - 1,
              },
            }));
          }
        }}
      />
      <S.Page>
        {exteriorColor.count}/{exteriorColor.page}
      </S.Page>
      <S.ArrowRightButton
        {...arrowProps}
        onClick={() => {
          if (exteriorColor.count < exteriorColor.page) {
            setTrimState((prevState) => ({
              ...prevState,
              exteriorColor: {
                ...prevState.exteriorColor,
                position: exteriorColor.position + 260,
                count: exteriorColor.count + 1,
              },
            }));
          }
        }}
      />
    </S.NextColor>
  );
}

export default NextColor;
