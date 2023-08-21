import { useEffect } from 'react';
import { useData, TotalPrice } from '../../utils/Context';
import { ESTIMATION, TYPE } from './constants';
import * as S from './style';
import PriceStaticBar from '../../components/PriceStaticBar';
import Preview from './Preview';
import Info from './Info';
import Detail from './Detail';
import HMGArea from './HMGArea';
import Footer from './Footer';

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
  const data = useData();
  const SelectModel = data.trim.fetchData.find((model) => model.id === data.trim.id);
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

  useEffect(() => {
    async function fetchData() {
      if (!data.estimation.isFetch && data.page === 6) {
        data.setTrimState((prevState) => ({
          ...prevState,
          estimation: {
            ...prevState.estimation,
            isFetch: true,
          },
        }));
      }
    }

    fetchData();
  }, [data]);

  return data.estimation.isFetch ? (
    <S.Estimation>
      <Preview />
      <S.Estimation>
        <Info />
        <S.PageContents>
          <Detail data={DATA} />
          <HMGArea data={MOCK_HMGDATA} />
        </S.PageContents>
        <Footer />
      </S.Estimation>

      <PriceStaticBar
        min={SelectModel?.minPrice}
        max={SelectModel?.maxPrice}
        price={TotalPrice(data.price)}
      />
    </S.Estimation>
  ) : (
    <>Loading...</>
  );
}

export default Estimation;
