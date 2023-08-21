import * as S from './style';
import PackageInfo from './PackageInfo';
import HMGArea from '../HMGArea';

function Description({ optionInfo, setImageUrl }) {
  return (
    <S.Description>
      {optionInfo?.package ? (
        <PackageInfo
          name={optionInfo?.name}
          options={optionInfo?.options}
          setImageUrl={setImageUrl}
        />
      ) : (
        <>
          <div>{optionInfo?.description}</div>
          {optionInfo?.hmgData?.length !== 0 && <HMGArea data={optionInfo?.hmgData} />}
        </>
      )}
    </S.Description>
  );
}

export default Description;
