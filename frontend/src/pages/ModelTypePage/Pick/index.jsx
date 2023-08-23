import { PICK } from '../constants';
import * as S from './style';
import PickTitle from '../../../components/PickTitle';
import PickCard from './PickCard';

function Pick({ modelType }) {
  const pickTitleProps = { mainTitle: PICK.TITLE };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />
      <S.PickModel>
        {modelType.fetchData.map((data) => (
          <PickCard key={data.type} data={data} />
        ))}
      </S.PickModel>
    </S.Pick>
  );
}

export default Pick;
