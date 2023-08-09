import * as S from './style';
import { useData } from '../../../utils/Context';
import { TITLE, BUTTON, ROUND_BUTTON } from './constants';
import PickTitle from '../../../components/PickTitle';
import PickCard from './PickCard';

import RoundButton from '../../../components/RoundButton';
import Button from '../../../components/Button';

function Pick() {
  const { modelType } = useData();
  console.log(modelType.modelTypes);

  const pickTitleProps = { mainTitle: TITLE };
  const buttonProps = {
    type: BUTTON.TYPE,
    state: BUTTON.STATE,
    mainTitle: BUTTON.MAIN_TITLE,
  };

  const roundButtonProps = {
    type: ROUND_BUTTON.TYPE,
    state: ROUND_BUTTON.STATE,
    title: ROUND_BUTTON.TITLE,
  };

  return (
    <S.Pick>
      <PickTitle {...pickTitleProps} />

      <S.SelectModel>
        {modelType.modelTypes.map((type) => (
          <PickCard key={type.type} />
        ))}
      </S.SelectModel>

      <RoundButton {...roundButtonProps} />
      <Button {...buttonProps} />
    </S.Pick>
  );
}

export default Pick;
