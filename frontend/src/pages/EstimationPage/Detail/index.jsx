import { useData } from '../../../utils/Context';
import { DETAIL, ESTIMATION, TYPE } from '../constants';
import * as S from './style';
import DetailCard from './DetailCard';

function Detail() {
  const data = useData();
  const SelectExterior = data.exteriorColor.fetchData.find(
    (pick) => data.exteriorColor.code === pick.code,
  );
  const SelectInterior = data.interiorColor.fetchData.find(
    (pick) => data.interiorColor.code === pick.code,
  );

  const DATA = [
    {
      title: ESTIMATION.DATA.MODEL_TYPE,
      type: TYPE.PLUS,
      expand: true,
      data: [
        {
          title: ESTIMATION.DATA.POWER_TRAIN,
          name: data.modelType.powerTrainOption?.name,
          image: data.modelType.powerTrainOption?.imageUrl,
          price: data.modelType.powerTrainOption?.price,
        },
        {
          title: ESTIMATION.DATA.BODY_TYPE,
          name: data.modelType.bodyTypeOption?.name,
          image: data.modelType.bodyTypeOption?.imageUrl,
          price: data.modelType.bodyTypeOption?.price,
        },
        {
          title: ESTIMATION.DATA.WHEEL_DRIVE,
          name: data.modelType.wheelDriveOption?.name,
          image: data.modelType.wheelDriveOption?.imageUrl,
          price: data.modelType.wheelDriveOption?.price,
        },
      ],
    },
    {
      title: ESTIMATION.DATA.COLOR,
      type: TYPE.PLUS,
      expand: true,
      data: [
        {
          title: ESTIMATION.DATA.EXTERIOR_COLOR,
          name: SelectExterior?.name,
          image: SelectExterior?.colorImageUrl,
          price: SelectExterior?.price,
        },
        {
          title: ESTIMATION.DATA.INTERIOR_COLOR,
          name: SelectInterior?.name,
          image: SelectInterior?.colorImageUrl,
          price: SelectInterior?.price,
        },
      ],
    },
    {
      title: ESTIMATION.DATA.OPTION,
      type: TYPE.PLUS,
      expand: data.optionPicker.isExpend,
      data: [
        ...data.optionPicker.chosenOptionsData.map((optionData, index) => ({
          id: index,
          name: optionData.name,
          image: optionData?.imageUrl,
          price: optionData.price,
        })),
      ],
    },
    { title: ESTIMATION.DATA.CONSIGNMENT, type: TYPE.MINUS },
    { title: ESTIMATION.DATA.DISCOUNT_AND_POINT, type: TYPE.MINUS },
    { title: ESTIMATION.DATA.PAYMENT, type: TYPE.PAYMENT },
    { title: ESTIMATION.DATA.TAX_AND_ENROLL, type: TYPE.TAX },
    { title: ESTIMATION.DATA.INFO },
  ];
  return (
    <S.Detail>
      <S.Title>{DETAIL.TITLE}</S.Title>
      <S.Content>
        {DATA.map((item) => (
          <DetailCard key={item.title} data={item} />
        ))}
      </S.Content>
    </S.Detail>
  );
}

export default Detail;
