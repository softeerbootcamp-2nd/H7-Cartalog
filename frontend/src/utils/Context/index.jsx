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
        trimPrice: null,
        powerTrainPrice: null,
        bodyTypePrice: null,
        wheelDrivePrice: null,
        exteriorColorPrice: null,
        interiorColorPrice: null,
        optionPrice: null,
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
