import React, { createContext, useContext, useState, useMemo } from 'react';

const StateContext = createContext(null);

export function StateProvider({ children }) {
  const stateKeys = [
    'page',
    'trimId',
    'powerTrain',
    'bodyType',
    'wd',
    'exteriorColor',
    'interiorColor',
    'addOption',
    'modelType',
    'price',
  ];

  const initialState = stateKeys.reduce((acc, key) => {
    if (key === 'price') {
      acc[key] = {
        trimPrice: 100000,
        powerTrainPrice: 100000,
        bodyTypePrice: 100000,
        wheelDrivePrice: 100000,
        exteriorColorPrice: 100000,
        interiorColorPrice: 100000,
        optionPrice: 100000,
      };
    } else if (key === 'modelType') {
      acc[key] = {
        powerTrain: 1,
        wheelDrive: 2,
        bodyType: 1,
      };
    } else {
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
