import { useEffect } from 'react';
import { useData, TotalPrice } from '../../utils/Context';
import { ESTIMATION } from './constants';
import PriceStaticBar from '../../components/PriceStaticBar';
import Preview from './Preview';
import * as S from './style';
import Info from './Info';
import Detail from './Detail';
import HMGArea from './HMGArea';

const MOCK_HMGDATA = {
  trim: '르블랑',
  price: {
    min: 43460000,
    max: 47650000,
    current: 45800000,
  },
  value: 2500,
  similar: [
    {
      id: 1,
      value: 3422,
    },
    {
      id: 2,
      value: 3200,
    },
    {
      id: 3,
      value: 3000,
    },
    {
      id: 4,
      value: 2800,
    },
  ],
};

function Estimation() {
  const {
    setTrimState,
    page,
    trim,
    modelType,
    exteriorColor,
    interiorColor,
    price,
    estimation,
    optionPicker,
  } = useData();
  const SelectModel = trim.fetchData.find((model) => model.id === trim.id);
  const SelectExterior = exteriorColor.fetchData.find((data) => exteriorColor.code === data.code);
  const SelectInterior = interiorColor.fetchData.find((data) => interiorColor.code === data.code);

  console.log(optionPicker.isExpend);
  const DATA = [
    {
      title: ESTIMATION.DATA.MODEL_TYPE,
      expand: true,
      data: [
        {
          title: ESTIMATION.DATA.POWER_TRAIN,
          name: modelType.powerTrainOption?.name,
          image: modelType.powerTrainOption?.imageUrl,
          price: modelType.powerTrainOption?.price,
        },
        {
          title: ESTIMATION.DATA.BODY_TYPE,
          name: modelType.bodyTypeOption?.name,
          image: modelType.bodyTypeOption?.imageUrl,
          price: modelType.bodyTypeOption?.price,
        },
        {
          title: ESTIMATION.DATA.WHEEL_DRIVE,
          name: modelType.wheelDriveOption?.name,
          image: modelType.wheelDriveOption?.imageUrl,
          price: modelType.wheelDriveOption?.price,
        },
      ],
    },
    {
      title: ESTIMATION.DATA.COLOR,
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
      expand: optionPicker.isExpend,
      data: [
        ...optionPicker.chosenOptionsData.map((optionData, index) => ({
          id: index,
          name: optionData.name,
          image: optionData?.imageUrl,
          price: optionData.price,
        })),
      ],
    },
    { title: ESTIMATION.DATA.CONSIGNMENT },
    { title: ESTIMATION.DATA.DISCOUNT_AND_POINT },
    { title: ESTIMATION.DATA.PAYMENT },
    { title: ESTIMATION.DATA.TAX_AND_ENROLL },
    { title: ESTIMATION.DATA.INFO },
  ];

  useEffect(() => {
    async function fetchData() {
      if (!estimation.isFetch && page === 6) {
        setTrimState((prevState) => ({
          ...prevState,
          estimation: {
            ...prevState.estimation,
            isFetch: true,
          },
        }));
      }
    }

    fetchData();
  }, [page]);

  return estimation.isFetch ? (
    <S.Estimation>
      <Preview />
      <S.Estimation>
        <Info />
        <S.PageContents>
          <Detail data={DATA} />
          <HMGArea data={MOCK_HMGDATA} />
        </S.PageContents>
      </S.Estimation>
      <PriceStaticBar
        min={SelectModel?.minPrice}
        max={SelectModel?.maxPrice}
        price={TotalPrice(price)}
      />
    </S.Estimation>
  ) : (
    <>Loading...</>
  );
}

export default Estimation;
