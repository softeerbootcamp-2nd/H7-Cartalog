import React, { createContext, useContext, useState, useMemo } from 'react';

const StateContext = createContext(null);

export function StateProvider({ children }) {
  const stateKeys = [
    'page',
    'trim',
    'modelType',
    'exteriorColor',
    'interiorColor',
    'optionPicker',
    'price',
  ];

  const initialState = stateKeys.reduce((acc, key) => {
    switch (key) {
      case 'price':
        acc[key] = {
          trimPrice: 0,
          powerTrainPrice: 0,
          bodyTypePrice: 0,
          wheelDrivePrice: 0,
          exteriorColorPrice: 0,
          interiorColorPrice: 0,
          optionPrice: 0,
        };
        break;
      case 'trim':
        acc[key] = {
          trimFetch: [],
          isTrimFetch: false,
          trimId: 2,
        };
        break;
      case 'modelType':
        acc[key] = {
          modelTypeFetch: [],
          isModelTypeFetch: false,
          pickId: 1,
          powerTrainType: '파워트레인/성능',
          bodyTypeType: '바디타입',
          wheelDriveType: '구동방식',
          powerTrainId: null,
          bodyTypeId: null,
          wheelDriveId: null,
          powerTrainOption: null,
          bodyTypeOption: null,
          wheelDriveOption: null,
        };
        break;
      case 'exteriorColor':
        acc[key] = {
          exteriorColorFetch: [],
          isExteriorColorFetch: false,
          exteriorColorCode: null,
          exteriorColorName: '어비스 블랙 펄',
          exteriorColorCarImageUrl:
            'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade',
          rotate: false,
        };
        break;
      case 'interiorColor':
        acc[key] = {
          dataFetch: [],
          interiorColorId: 'I49',
          pick: 'A22',
          pickName: '퀼팅천연(블랙)',
          pickCarImageUrl:
            'https://want-car-image.s3.ap-northeast-2.amazonaws.com/palisade/le-blanc/options/10_driverseat_s.jpg',
        };
        break;
      default:
        acc[key] = null; // 다른 키들은 null로 초기화
    }
    return acc;
  }, {});

  const [trimState, setTrimState] = useState(initialState);

  const contextValue = useMemo(() => {
    return {
      ...trimState,
      setTrimState,
    };
  }, [trimState]);

  return <StateContext.Provider value={contextValue}>{children}</StateContext.Provider>;
}

export function useData() {
  const context = useContext(StateContext);
  if (!context) {
    throw new Error('There is No Provider');
  }
  return context;
}

export function TotalPrice(priceObj) {
  const prices = Object.values(priceObj);
  const totalPrice = prices.reduce((acc, price) => acc + (price || 0), 0);
  return totalPrice;
}
