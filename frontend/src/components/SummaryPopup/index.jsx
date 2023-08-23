import { useState, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { useData, TotalPrice } from '../../utils/Context';
import { SUMMARY, SUMMARY_DATA } from './constants';
import { ReactComponent as CloseIcon } from '../../../assets/icons/cancel.svg';
import * as S from './style';
import Toggle from '../Toggle';
import InfoPanel from './InfoPanel';

function SummaryPopup({ show, close }) {
  const { setTrimState, page, exteriorColor, interiorColor, summary, optionPicker } = useData();
  const data = useData();
  const [toggle, setToggle] = useState(false);
  const DATA = [
    [
      { title: SUMMARY_DATA.MODEL, content: SUMMARY_DATA.PALISADE },
      { title: SUMMARY_DATA.TRIM, content: data.trim.name, price: data.price.trimPrice },
    ],
    [
      {
        title: SUMMARY_DATA.POWER_TRAIN,
        content: data.modelType.powerTrainOption?.name,
        price: data.modelType.powerTrainOption?.price,
      },
      {
        title: SUMMARY_DATA.BODY_TYPE,
        content: data.modelType.bodyTypeOption?.name,
        price: data.modelType.bodyTypeOption?.price,
      },
      {
        title: SUMMARY_DATA.WHEEL_DRIVE,
        content: data.modelType.wheelDriveOption?.name,
        price: data.modelType.wheelDriveOption?.price,
      },
    ],
    [
      {
        title: SUMMARY_DATA.EXTERIOR_COLOR,
        content: data.exteriorColor.name,
        price: data.price.exteriorColorPrice,
      },
      {
        title: SUMMARY_DATA.INTERIOR_COLOR,
        content: data.interiorColor.name,
        price: data.price.interiorColorPrice,
      },
    ],
    [
      ...(optionPicker.chosenOptionsData.length !== 0
        ? optionPicker.chosenOptionsData.map((optionData, index) => ({
            title: index === 0 ? SUMMARY_DATA.OPTION : '',
            content: optionData.name,
            price: optionData.price,
          }))
        : [
            {
              title: SUMMARY_DATA.OPTION,
              content: '-',
            },
          ]),
    ],
  ];

  useEffect(() => {
    if (!page || page === 1) return;
    async function fetchData() {
      const response = await fetch(
        `http://13.209.9.2/models/images?exteriorColorCode=${exteriorColor.code}&interiorColorCode=${interiorColor.code}`,
      );
      const dataFetch = await response.json();

      setTrimState((prevState) => ({
        ...prevState,
        summary: {
          sideImage: dataFetch.sideExteriorImageUrl,
          interiorImage: dataFetch.interiorImageUrl,
        },
      }));
    }
    fetchData();
    setToggle(false);
  }, [exteriorColor, interiorColor]);

  return (
    show &&
    createPortal(
      <>
        <S.Overlay />
        <S.SummaryPopup>
          <S.Header>
            <S.Title>{SUMMARY.TITLE}</S.Title>
            <S.CloseButton>
              <CloseIcon onClick={close} />
            </S.CloseButton>
          </S.Header>
          <S.Contents>
            <S.LeftArea>
              <img
                src={summary.interiorImage}
                alt="interior"
                style={toggle ? null : { display: 'none' }}
              />
              <img
                src={summary.sideImage}
                alt="exterior"
                style={toggle ? { display: 'none' } : null}
              />
              <Toggle state={toggle} setState={setToggle} big />
            </S.LeftArea>
            <S.RightArea>
              <InfoPanel data={DATA} />
            </S.RightArea>
          </S.Contents>
          <S.TotalPrice>
            <S.TextTitle>{SUMMARY.PRICE_TITLE}</S.TextTitle>
            <S.PriceTitle>
              {TotalPrice(data.price).toLocaleString()}
              {SUMMARY.WON}
            </S.PriceTitle>
          </S.TotalPrice>
        </S.SummaryPopup>
      </>,
      document.querySelector('#modal'),
    )
  );
}

export default SummaryPopup;
